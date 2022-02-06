package org.unibl.etf.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.*;

import org.unibl.etf.model.Person;

import com.google.gson.Gson;

@Path("/person")
public class APIService {

	PersonService service = new PersonService();

	@GET
	@Path("/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersonByToken(@PathParam("token") int token) {
		Person p = service.getPersonByToken(token);
		if (p != null) {
			return Response.status(200).entity(new Gson().toJson(p)).build();
		} else
			return Response.status(404).build();
	}

	@GET
	@Path("map/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMapByToken(@PathParam("token") int token) {
		String[][] map = service.getPosition(token);
		if (map != null) {
			return Response.status(200).entity(map).build();
		} else
			return Response.status(404).build();
	}

	@GET
	@Path("time/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTimeByToken(@PathParam("token") int token) {
		String time = service.getTime(token);
		if (time != null) {
			return Response.status(200).entity(time).build();
		} else
			return Response.status(404).build();

	}

	@POST
	@Path("time/{token}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setNewTime(@PathParam("token") int token, String time) {
		if (service.setTime(token, time)) {
			return Response.status(200).entity(time).build();
		} else
			return Response.status(500).entity("Greska neka").build();
	}

	@POST
	@Path("map/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setNewPosition(@PathParam("token") int token, String[][] map) {
		if (service.setPosition(token, map)) {
			return Response.status(200).entity(new Gson().toJson(map)).build();
		} else
			return Response.status(500).entity("Greska neka").build();
	}

	@POST
	@Path("message/{token}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response setMessage(@PathParam("token") int token, String message) {
		if (service.setMessage(message, token)) {
			return Response.status(200).entity(message).build();
		} else
			return Response.status(500).entity("Greska neka").build();

	}

	@GET
	@Path("message/{token}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getMessage(@PathParam("token") int token) {
		String message = service.getMessage(token);
		if (message != null) {
			return Response.status(200).entity(message).build();
		} else
			return Response.status(404).build();

	}

	@GET
	@Path("config")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getConfig() {
		String message = service.getConfig();
		if (message != null) {
			return Response.status(200).entity(message).build();
		} else
			return Response.status(404).build();

	}

}
