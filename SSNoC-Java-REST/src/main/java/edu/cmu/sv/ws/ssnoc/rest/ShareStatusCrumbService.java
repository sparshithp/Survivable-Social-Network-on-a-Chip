package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.po.StatusCrumbPO;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumbDTO;

@Path("/statuscrumbs")
public class ShareStatusCrumbService extends BaseService {
	//Retrieve a user's status history	GET	/statuscrumbs/userName		array of StatusCrumb		No		Share Status	
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	@XmlElementWrapper(name = "statusCrumbs")
	public List<StatusCrumbDTO> loadStatusCrumbsByName(@PathParam("userName") String userName) {
		Log.enter();

		List<StatusCrumbDTO> dtos = null;
		try {
			List<StatusCrumbPO> pos = DAOFactory.getInstance().getShareStatusDAO().loadStatusCrumbsByName(userName);

			dtos = new ArrayList<StatusCrumbDTO>();
			for (StatusCrumbPO po : pos) {
				StatusCrumbDTO dto = ConverterUtils.convert(po);
				dtos.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(dtos);
		}
		return dtos;
	}	
	
}
