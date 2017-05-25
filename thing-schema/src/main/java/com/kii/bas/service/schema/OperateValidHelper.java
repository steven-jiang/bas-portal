//package com.kii.bas.service.schema;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.kii.bas.service.schema.entity.ActionObject;
//import com.kii.bas.service.schema.entity.DeviceSchema;
//import com.kii.bas.service.schema.entity.PointObject;
//import com.kii.bas.service.schema.entity.SchemaObj;
//import com.kii.bas.service.schema.exception.FailReason;
//import com.kii.bas.service.schema.exception.VerifyException;
//import com.kii.extension.sdk.entity.thingif.Action;
//import com.kii.extension.sdk.entity.thingif.ThingCommand;
//import com.kii.extension.sdk.entity.thingif.ThingStatus;
//
//@Component
//public class OperateValidHelper {
//
//
//	@Autowired
//	private ThingSchemaService service;
//
//	public ThingCommand convert(String deviceType, ThingStatus status) {
//
//		DeviceSchema schema = service.getCompleteDeviceSchema(deviceType);
//
//
//		ThingCommand cmd = new ThingCommand();
//
//		for (Map.Entry<String, Object> entry : status.getFields().entrySet()) {
//
//			PointObject point = schema.getPointsMap().get(entry.getKey());
//
//			String actionName = point.getWriteActionName();
//			if (StringUtils.isBlank(actionName)) {
//				actionName = entry.getKey();
//			}
//
//			Action action = new Action();
//			action.setField(entry.getKey(), entry.getValue());
//
//			cmd.addAction(actionName, action);
//		}
//
//		return cmd;
//	}
//
//
//	public void validPointOperate(String schemaType, ThingStatus status, boolean isWrite) throws VerifyException {
//
//
//		DeviceSchema schema = service.getCompleteDeviceSchema(schemaType);
//
//		if (schema == null) {
//			throw new VerifyException(FailReason.getSchemaInstance(schemaType));
//		}
//
//		List<FailReason> failList = new ArrayList<>();
//
//		for (Map.Entry<String, Object> entry : status.getFields().entrySet()) {
//			PointObject point = schema.getPointsMap().get(entry.getKey());
//
//			FailReason reason = FailReason.getPointInstance(schemaType, entry.getKey());
//
//			if (point == null) {
//				failList.add(reason);
//				continue;
//			}
//
//
//			if (!point.getWritable() && isWrite) {
//				reason.setReason(FailReason.Reason.InvalidOperate);
//				continue;
//			}
//
//			FailReason.Reason rea = verifyValue(entry.getValue(), point.getObj());
//
//			if (rea != FailReason.Reason.Right) {
//				reason.setReason(rea);
//				failList.add(reason);
//			}
//		}
//
//		if (!failList.isEmpty()) {
//			throw new VerifyException(failList);
//		}
//
//	}
//
//	public void validActionOperate(String schemaName, ThingCommand command) {
//
//		DeviceSchema schema = service.getCompleteDeviceSchema(schemaName);
//
//		if (schema == null) {
//			throw new VerifyException(FailReason.getSchemaInstance(schemaName));
//		}
//
//		List<FailReason> failList = new ArrayList<>();
//
//		for (Map<String, Action> actionMap : command.getActions()) {
//
//			for (Map.Entry<String, Action> entry : actionMap.entrySet()) {
//
//				ActionObject action = schema.getActionsMap().get(entry.getKey());
//
//
//				if (action == null) {
//					FailReason reason = FailReason.getActionInstance(schemaName, entry.getKey());
//					failList.add(reason);
//					continue;
//				}
//
//				Action act = entry.getValue();
//
//				Map<String, SchemaObj> actObjs = action.getFields();
//
//				for (Map.Entry<String, Object> actEntry : act.getFields().entrySet()) {
//
//					String fieldName = actEntry.getKey();
//
//					SchemaObj obj = actObjs.get(fieldName);
//
//					FailReason reason = FailReason.getActionInstance(schemaName, entry.getKey(), fieldName);
//					reason.setFieldName(fieldName);
//
//
//					if (obj == null) {
//						reason.setReason(FailReason.Reason.NameNotExist);
//						failList.add(reason);
//						break;
//					}
//
//					Object value = actEntry.getValue();
//					FailReason.Reason rea = verifyValue(value, obj);
//
//					if (rea != FailReason.Reason.Right) {
//						reason.setReason(rea);
//						failList.add(reason);
//					}
//
//				}
//
//			}
//		}
//
//		if (!failList.isEmpty()) {
//			throw new VerifyException(failList);
//		}
//
//
//	}
//
//
//	private FailReason.Reason verifyValue(Object value, SchemaObj obj) {
//
//		if (value == null) {
//			return FailReason.Reason.NullValue;
//		}
//		try {
//			switch (obj.getType()) {
//
//				case IntEnum: {
//					int val = (int) value;
//					if (!obj.getEnumValues().contains(value)) {
//						return FailReason.Reason.NotInEnum;
//					}
//					break;
//				}
//				case StrEnum: {
//					String val = (String) value;
//					if (!obj.getEnumValues().contains(value)) {
//						return FailReason.Reason.NotInEnum;
//					}
//					break;
//				}
//				case Int: {
//					int val = (int) value;
//					Number limit = obj.getLowerLimit();
//					if (limit == null) {
//						break;
//					}
//					if (limit.intValue() < val) {
//						return FailReason.Reason.OutOfRange;
//					}
//
//
//					limit = obj.getUpperLimit();
//					if (limit == null) {
//						break;
//					}
//					if (limit.intValue() > val) {
//						return FailReason.Reason.OutOfRange;
//					}
//					break;
//				}
//				case Long: {
//					Long val = (Long) value;
//
//					Number limit = obj.getLowerLimit();
//					if (limit == null) {
//						break;
//					}
//					if (limit.longValue() < val) {
//						return FailReason.Reason.OutOfRange;
//					}
//
//
//					limit = obj.getUpperLimit();
//					if (limit == null) {
//						break;
//					}
//					if (limit.longValue() > val) {
//						return FailReason.Reason.OutOfRange;
//					}
//					break;
//				}
//				case Float: {
//					Number num = (Number) value;
//
//					double val = num.doubleValue();
//
//					Number limit = obj.getLowerLimit();
//					if (limit == null) {
//						break;
//					}
//					if (limit.doubleValue() < val) {
//						return FailReason.Reason.OutOfRange;
//					}
//
//
//					limit = obj.getUpperLimit();
//					if (limit == null) {
//						break;
//					}
//					if (limit.doubleValue() > val) {
//						return FailReason.Reason.OutOfRange;
//					}
//					break;
//				}
//				case String: {
//					String val = (String) value;
//					if (obj.getLength() != null) {
//						if (val.length() > obj.getLength()) {
//							return FailReason.Reason.OutOfRange;
//						}
//					}
//				}
//				case Boolean: {
//
//					int val = (int) value;
//					if (val != 1 && val != 0) {
//						return FailReason.Reason.OutOfRange;
//					}
//				}
//				default:
//					break;
//			}
//		} catch (ClassCastException ex) {
//			return FailReason.Reason.InvalidType;
//		}
//		return FailReason.Reason.Right;
//
//	}
//
//
//}
