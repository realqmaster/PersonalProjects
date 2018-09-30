package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.CallActivity;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaExecutionListener;
import org.camunda.bpm.model.xml.ModelInstance;
import org.camunda.bpm.model.xml.instance.DomElement;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

public class Converter {

	private static final String TASK_TEMPLATE_NAME = "RemoteCallTask_";
	private static final String PACKAGE_PATH = "it.euris.lisa.process.";
	private static final String TARGET_PATH = "G:\\STS\\camunda-modeler-2.0.3-win-x64\\target.bpmn";
	private static final String SOURCE_PATH = "G:\\STS\\camunda-modeler-2.0.3-win-x64\\source.bpmn";
	private static final String CALLBACK_LISTENER_PATH = "it.euris.lisa.process.listeners.RemoteCallbackListener";

	private ModelInstance modelInstance;

	public BpmnModelInstance readSource() throws Exception {

		return Bpmn.readModelFromFile(new File(SOURCE_PATH));

	}

	public BpmnModelInstance convertRemoteCA(BpmnModelInstance bpmn) throws IOException {
		modelInstance = bpmn;
		List<CallActivity> callActivities = new ArrayList<CallActivity>(
				bpmn.getModelElementsByType(CallActivity.class));
		for (CallActivity callActivity : callActivities) {
			if (shouldBeConverted(callActivity)) {
				List<Process> processes = new ArrayList<>(bpmn.getDefinitions().getChildElementsByType(Process.class));
				Process process = processes.get(0);
				ServiceTask task = createElement(process, callActivity.getId(), ServiceTask.class);
				task.setCamundaClass(PACKAGE_PATH + TASK_TEMPLATE_NAME + callActivity.getId());
				callActivity.replaceWithElement(task);

			}
		}

		return bpmn;

	}

	public BpmnModelInstance appendCallbackListener(BpmnModelInstance bpmn) {
		modelInstance = bpmn;
		List<Process> processes = new ArrayList<>(bpmn.getDefinitions().getChildElementsByType(Process.class));
		Process process = processes.get(0);
		ExtensionElements extensionElements = process.getExtensionElements();
		
		if (extensionElements == null) {
			  extensionElements = modelInstance.newInstance(ExtensionElements.class);
			  process.setExtensionElements(extensionElements);
			}
		
		CamundaExecutionListener callbackListener = extensionElements.addExtensionElement(CamundaExecutionListener.class);
		callbackListener.setCamundaEvent("end");
		callbackListener.setCamundaClass(CALLBACK_LISTENER_PATH);
		return bpmn;

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

	protected <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement, String id,
			Class<T> elementClass) {
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

			BpmnModelInstance bpmn = c.readSource();
			bpmn = c.convertRemoteCA(bpmn);
			bpmn = c.appendCallbackListener(bpmn);
			Bpmn.validateModel(bpmn);
			Bpmn.writeModelToFile(new File(TARGET_PATH), bpmn);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
