package com.kii.bas.portal.web.controller;


import java.io.IOException;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.kii.bas.commons.json.SafeObjectMapper;
import com.kii.bas.commons.sysmonitor.SysMonitorMsg;
import com.kii.bas.commons.sysmonitor.SysMonitorQueue;


@Controller
public class SysNoticeHandler extends TextWebSocketHandler {
	
	
	@Autowired
	private SafeObjectMapper mapper;
	
	private Function<SysMonitorMsg, Boolean> callback;
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		
		
		SysMonitorQueue.getInstance().registerFire(session.getId(), notice -> {
			
			if (notice == null) {
				return true;
			}
			
			if (!session.isOpen()) {
				
				return false;
			}
			
			
			TextMessage msg = new TextMessage(mapper.writeValueAsString(notice));
			try {
				session.sendMessage(msg);
				
				return true;
			} catch (IOException ex) {
				return false;
			}
			
		});
		
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		SysMonitorQueue.getInstance().unRegisterFire(session.getId());
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		SysMonitorQueue.getInstance().unRegisterFire(session.getId());
		
	}
	
}
