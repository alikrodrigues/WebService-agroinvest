package br.com.agroinvest.rest.endpoint;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import br.com.agroinvest.model.Categoria;
import br.com.agroinvest.model.UnidadeMedida;

/**
 * 
 */
@Stateless
@Path("/categoria")
public class CategoriaEndpoint {
	@PersistenceContext(unitName = "ws-agroinvest-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(Categoria entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(CategoriaEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Categoria entity = em.find(Categoria.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<Categoria> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Categoria c WHERE c.id = :entityId ORDER BY c.id",
						Categoria.class);
		findByIdQuery.setParameter("entityId", id);
		Categoria entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}
	
	@GET
	@Path("/{categoria}")
	@Produces("application/json")
	public Response findById(@PathParam("categoria") String categoria) {
		TypedQuery<Categoria> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT u FROM Categoria u WHERE u.descricao = :categoria ORDER BY u.id",
						Categoria.class);
		findByIdQuery.setParameter("categoria", categoria);
		Categoria entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Categoria> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<Categoria> findAllQuery = em.createQuery(
				"SELECT DISTINCT c FROM Categoria c ORDER BY c.id",
				Categoria.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<Categoria> results = findAllQuery.getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Categoria entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!id.equals(entity.getId())) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (em.find(Categoria.class, id) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}
}
