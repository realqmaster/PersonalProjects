package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.codearte.jfairy.Fairy;

@RestController
@RequestMapping("")
public class Tester {

	@Autowired
	ProjectRepo repo;

	@GetMapping("/test")
	public ResponseEntity<?> test(@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "perPage", defaultValue = "20") String perPage,
			@RequestParam(value = "currentPage", defaultValue = "1") String currentPage)

	{

		List<Project> resultset = retrieveRawData();
		List<ProjectResponseAggregated> aggregated = aggregate(resultset);
		applySorting(aggregated, orderBy);		
		List<ProjectResponseAggregated> page = getSelectedPage(perPage, currentPage, aggregated);
		return ResponseEntity.ok(page);
	}

	private List<ProjectResponseAggregated> getSelectedPage(String perPage, String currentPage,
			List<ProjectResponseAggregated> aggregated) {
		List<ProjectResponseAggregated> page = new ArrayList<>();

		int offset = 0;
		Integer iPerPage = Integer.valueOf(perPage);
		Integer iCurrentPage = Integer.valueOf(currentPage);

		offset = iPerPage * (iCurrentPage - 1);

		System.out.println("size " + aggregated.size());
		System.out.println("offset " + offset);

		if (aggregated.size() < offset + iPerPage || iPerPage > aggregated.size()) {
			page = aggregated.subList(offset, aggregated.size());
		} else {
			page = aggregated.subList(offset, offset + iPerPage);
		}
		return page;
	}

	private List<Project> retrieveRawData() {
		Fairy fairy = Fairy.create();
		String[] projectNames = { "foo", "bar", "baz" };
		repo.deleteAll();

		for (int i = 0; i < 30; i++) {

			int roll = fairy.baseProducer().randomBetween(0, 99);

			Project project = new Project();
			project.setId(UUID.randomUUID().toString());
			project.setMajor(String.valueOf(fairy.baseProducer().randomBetween(1, 5)));
			project.setMinor(String.valueOf(fairy.baseProducer().randomBetween(1, 10)));
			project.setCreationDate(fairy.dateProducer().randomDateInThePast(2).toDate());
			project.setEditDate(fairy.dateProducer().randomDateInTheFuture().toDate());
			project.setName(fairy.baseProducer().randomElement(projectNames));
			project.setDescription(fairy.textProducer().sentence());

			if (roll > 50) {
				project.setEditDate(null);
			}

			repo.save(project);
		}

		List<Project> resultset = new ArrayList<>();
		repo.findAll().forEach(resultset::add);
		return resultset;
	}

	private List<ProjectResponseAggregated> aggregate(List<Project> source) {
		Map<String, List<Project>> result = source.stream().collect(Collectors.groupingBy(Project::getName));
		List<ProjectResponseAggregated> processed = new ArrayList<>();

		for (Entry<String, List<Project>> entry : result.entrySet()) {

			List<Version> versionList = new ArrayList<>();

			for (Project project : entry.getValue()) {
				Version version = new Version();
				BeanUtils.copyProperties(project, version);
				versionList.add(version);
			}

			Comparator<Version> comparator = Comparator.comparing(Version::getIntMajor).thenComparing(Version::getIntMinor)
					.reversed();
			Collections.sort(versionList, comparator);
			ProjectResponseAggregated p = new ProjectResponseAggregated();
			p.setName(entry.getKey());
			p.setVersions(versionList);
			processed.add(p);
		}

		return processed;
	}

	private void applySorting(List<ProjectResponseAggregated> aggregated, String orderBy) {

		String sortParameter = "";
		String sortDirection = "";

		if (!StringUtils.isEmpty(orderBy)) {
			sortParameter = orderBy.split(" ")[0];
			sortDirection = orderBy.split(" ")[1];
		}

		Comparator<ProjectResponseAggregated> comparator = null;

		switch (sortParameter) {
		case "editDate": {
			comparator = Comparator
					.nullsLast(Comparator.comparing((ProjectResponseAggregated p) -> p.getLatestVersion().getEditDate(),
							Comparator.nullsLast(Comparator.naturalOrder())));

		}
		default: {
			comparator = Comparator.nullsLast(
					Comparator.comparing((ProjectResponseAggregated p) -> p.getLatestVersion().getCreationDate(),
							Comparator.nullsLast(Comparator.naturalOrder())));

		}
		}

		if (comparator != null) {
			if (sortDirection.equals("DESC")) {
				comparator = comparator.reversed();
			}
			Collections.sort(aggregated, comparator);
			System.out.println("Sorted!");
		}

	}

}
