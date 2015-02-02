package edu.cmu.sv.ws.ssnoc.common.utils;

import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumbDTO;
import edu.cmu.sv.ws.ssnoc.dto.User;

/**
 * This is a utility class used to convert PO (Persistent Objects) and View
 * Objects into DTO (Data Transfer Objects) objects, and vice versa. <br/>
 * Rather than having the conversion code in all classes in the rest package,
 * they are maintained here for code re-usability and modularity.
 * 
 */
public class ConverterUtils {
	/**
	 * Convert UserPO to User DTO object.
	 * 
	 * @param po
	 *            - User PO object
	 * 
	 * @return - User DTO Object
	 */
	public static final User convert(UserPO po) {
		if (po == null) {
			return null;
		}

		User dto = new User();
		dto.setUserId(po.getUserId());
		dto.setUserName(po.getUserName());
		dto.setStatus(po.getStatus());
		dto.setLocationDesc(po.getLocationDesc());
		dto.setLastPostTime(po.getLastPostTime());
		dto.setPassword(po.getPassword());
		return dto;
	}

	/**
	 * Convert User DTO to UserPO object
	 * 
	 * @param dto
	 *            - User DTO object
	 * 
	 * @return - UserPO object
	 */
	public static final UserPO convert(User dto) {
		if (dto == null) {
			return null;
		}

		UserPO po = new UserPO();
		po.setUserId(dto.getUserId());
		po.setUserName(dto.getUserName());
		po.setPassword(dto.getPassword());
		po.setStatus(dto.getStatus());
		//po.setSalt(dto.getSalt());
		po.setLocationDesc(dto.getLocationDesc());

		return po;
	}
	
	/**
	 * Convert Message DTO to MessagePO object
	 * 
	 * @param dto
	 *            - Message DTO object
	 * 
	 * @return - MessagePO object
	 */
	public static final MessagePO convert(Message dto) {
		if (dto == null) {
			return null;
		}

		MessagePO po = new MessagePO();
		po.setContent(dto.getContent());
		po.setMessageType(dto.getMessageType());
		po.setTarget(dto.getTarget());
		po.setPostedAt(dto.getPostedAt());
		po.setMessageID(dto.getMessageID());
		po.setAuthor(dto.getAuthor());
		return po;
	}

	public static final Message convert(MessagePO po) {
		if (po == null) {
			return null;
		}

		Message dto = new Message();
		dto.setContent(po.getContent());
		dto.setMessageType(po.getMessageType());
		dto.setTarget(po.getTarget());
		dto.setPostedAt(po.getPostedAt());
		dto.setMessageID(po.getMessageID());
		dto.setAuthor(po.getAuthor());
		return dto;
	}

	
	public static final StatusCrumbDTO convert(StatusCrumbPO po){
		if (po == null) {
			return null;
		}

		StatusCrumbDTO dto = new StatusCrumbDTO();
		dto.setCrumbId(po.getCrumbId());
		dto.setUserName(po.getUserName());
		dto.setStatusCode(po.getStatusCode());
		dto.setLocationDesc(po.getLocationDesc());
		dto.setCreatedAt(po.getCreatedAt());		
		return dto;
	}
	
	public static final StatusCrumbPO convert(StatusCrumbDTO dto){
		if (dto == null) {
			return null;
		}

		StatusCrumbPO po = new StatusCrumbPO();
		po.setCrumbId(dto.getCrumbId());
		po.setUserName(dto.getUserName());
		po.setStatusCode(dto.getStatusCode());
		po.setLocationDesc(dto.getLocationDesc());
		po.setCreatedAt(dto.getCreatedAt());		
		return po;
	}	
	
	
}
