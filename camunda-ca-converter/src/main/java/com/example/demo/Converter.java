package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.xml.ModelInstance;
import org.camunda.bpm.model.xml.instance.DomElement;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.w3c.dom.Document;

public class Converter {

	private static final String TARGET_PATH = "G:\\STS\\camunda-modeler-2.0.3-win-x64\\target.bpmn";
	private static final String SOURCE_PATH = "G:\\STS\\camunda-modeler-2.0.3-win-x64\\source.bpmn";
	
	private ModelInstance modelInstance;

	public Document readSource() throws Exception {

		File fXmlFile = new File(SOURCE_PATH);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		return doc;

	}

	public void convertRemoteCA() throws IOException {

		BpmnModelInstance bpmn = Bpmn.readModelFromFile(new File(SOURCE_PATH));
		modelInstance = bpmn;
		List<CallActivity> callActivities = new ArrayList<CallActivity>(
				bpmn.getModelElementsByType(CallActivity.class));
		for (CallActivity callActivity : callActivities) {
			if (shouldBeConverted(callActivity)) {
				// TODO la conversione
				List<Process> processes = new ArrayList<>(bpmn.getDefinitions().getChildElementsByType(Process.class));
				Process process = processes.get(0);
				ServiceTask task  = createElement(process, callActivity.getId(), ServiceTask.class);
				task.setCamundaClass("it.euris.lisa.process.SOMECLASS");
				callActivity.replaceWithElement(task);
				
			}
		}
		
		Bpmn.validateModel(bpmn);
		Bpmn.writeModelToFile(new File(TARGET_PATH), bpmn);

	}

	private boolean shouldBeConverted(CallActivity callActivity) {
		Collection<ModelElementInstance> extensions = callActivity.getExtensionElements().getElements();
		for (ModelElementInstance ex : extensions) {
			List<DomElement> props = ex.getDomElement().getChildElements();
			for (DomElement prop : props) {
				if (prop.getAttribute("name").equals("task_type")
						&& prop.getAttribute("value").equals("external_call_activity")) {
					return true;
				}
			}
		}
		return false;
	}
	
	  protected <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement, String id, Class<T> elementClass) {
		    T element = modelInstance.newInstance(elementClass);
		    element.setAttributeValue("id", id, true);
		    parentElement.addChildElement(element);
		    return element;
		  }

		  public SequenceFlow createSequenceFlow(Process process, FlowNode from, FlowNode to) {
		    SequenceFlow sequenceFlow = createElement(process, from.getId() + "-" + to.getId(), SequenceFlow.class);
		    process.addChildElement(sequenceFlow);
		    sequenceFlow.setSource(from);
		    from.getOutgoing().add(sequenceFlow);
		    sequenceFlow.setTarget(to);
		    to.getIncoming().add(sequenceFlow);
		    return sequenceFlow;
		  }

	public static void main(String[] args) {
		Converter c = new Converter();
		try {
			c.convertRemoteCA();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
