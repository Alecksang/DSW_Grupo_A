package com.ucab.cmcapp.implementation;

import com.ucab.cmcapp.common.entities.Alerta;
import com.ucab.cmcapp.common.entities.User;
import com.ucab.cmcapp.common.entities.Alerta;
import com.ucab.cmcapp.common.util.CustomResponse;
import com.ucab.cmcapp.logic.commands.CommandFactory;
import com.ucab.cmcapp.logic.commands.alerta.atomic.GetAlertaByTipoAlertaCommand;
import com.ucab.cmcapp.logic.commands.alerta.composite.*;
import com.ucab.cmcapp.logic.commands.user.atomic.GetUserByEmailCommand;
import com.ucab.cmcapp.logic.commands.user.composite.CreateUserCommand;
import com.ucab.cmcapp.logic.commands.user.composite.GetUserCommand;
import com.ucab.cmcapp.logic.dtos.AlertaDto;
import com.ucab.cmcapp.logic.dtos.UserDto;
import com.ucab.cmcapp.logic.dtos.AlertaDto;
import com.ucab.cmcapp.logic.mappers.AlertaMapper;
import com.ucab.cmcapp.logic.mappers.UserMapper;
import com.ucab.cmcapp.logic.mappers.AlertaMapper;
import com.ucab.cmcapp.persistence.dao.AlertaDao;
import com.ucab.cmcapp.persistence.dao.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Path( "/alertas" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class AlertaService extends BaseService
{
    private static Logger _logger = LoggerFactory.getLogger( AlertaService.class );

    @GET
    @Path( "/{id}" )
    public Response getAlerta(@PathParam( "id" ) long alertaId )
    {
        Alerta entity;
        AlertaDto response;
        GetAlertaCommand command = null;
        //region Instrumentation DEBUG
        _logger.debug( "Get in AlertaService.getAlerta" );
        //endregion

        try
        {
            entity = AlertaMapper.mapDtoToEntity( alertaId );
            command = CommandFactory.createGetAlertaCommand( entity );
            command.execute();
            if(command.getReturnParam() != null){
                response = AlertaMapper.mapEntityToDto(command.getReturnParam());
            }else{
                return Response.status(Response.Status.OK).entity(new CustomResponse<>("No se puede Buscar por " + alertaId)).build();
            }
        }
        catch ( Exception e )
        {
            return Response.status(Response.Status.OK).entity(new CustomResponse<>("Error en Alerta " + alertaId)).build();

        }
        finally
        {
            if (command != null)
                command.closeHandlerSession();
        }

        _logger.debug( "Leaving AlertaService.getAlerta" );
        return Response.status(Response.Status.OK).entity(new CustomResponse<>(response,"Busqueda por Id Alerta: " + alertaId)).build();
    }


//    @GET
//    @Path( "/TipoAlerta/{TipoAlerta}" )
//    public Response getAlerta(@PathParam( "TipoAlerta" ) String tipoAlerta )
//    {
//        Alerta entity;
//        AlertaDto response;
//        GetAlertaByTipoAlertaCommand command = null;
//        //region Instrumentation DEBUG
//        _logger.debug( "Get in AlertaService.getAlerta" );
//        //endregion
//
//        try
//        {
//            entity = AlertaMapper.mapDtoToEntityTipoAlerta( tipoAlerta );
//            command = CommandFactory.createGetAlertaByTipoAlertaCommand( entity );
//            command.execute();
//            if(command.getReturnParam() != null){
//                response = AlertaMapper.mapEntityToDto(command.getReturnParam());
//            }else{
//                return Response.status(Response.Status.OK).entity(new CustomResponse<>("No se puede Buscar por " + tipoAlerta)).build();
//            }
//        }
//        catch ( Exception e )
//        {
//            return Response.status(Response.Status.OK).entity(new CustomResponse<>("Error en Alerta " + tipoAlerta)).build();
//
//        }
//        finally
//        {
//            if (command != null)
//                command.closeHandlerSession();
//        }
//
//        _logger.debug( "Leaving AlertaService.getAlerta" );
//        return Response.status(Response.Status.OK).entity(new CustomResponse<>(response,"Busqueda por tipo de alerta: " + tipoAlerta)).build();
//    }

    @GET
    @Path("/TipoAlerta/{TipoAlerta}")
    public Response getAlertas(@PathParam("TipoAlerta") List<String> tipoAlertas) {
        List<AlertaDto> responses = new ArrayList<>();
        GetAlertaByTipoAlertaCommand command = null;

        //region Instrumentation DEBUG
        _logger.debug("Get in AlertaService.getAlertas");
        //endregion

        try {
            for (String tipoAlerta : tipoAlertas) {
                Alerta entity = AlertaMapper.mapDtoToEntityTipoAlerta(tipoAlerta);
                command = CommandFactory.createGetAlertaByTipoAlertaCommand(entity);
                command.execute();
                if (command.getReturnParam() != null) {
                    AlertaDto response = AlertaMapper.mapEntityToDto(command.getReturnParam());
                    responses.add(response);
                } else {
                    return Response.status(Response.Status.OK).entity(new CustomResponse<>("No se puede Buscar por " + tipoAlerta)).build();
                }
            }
        } catch (Exception e) {
            return Response.status(Response.Status.OK).entity(new CustomResponse<>("Error en Alerta " + tipoAlertas)).build();
        } finally {
            if (command != null)
                command.closeHandlerSession();
        }

        //region Instrumentation DEBUG
        _logger.debug("Leaving AlertaService.getAlertas");
        //endregion

        return Response.status(Response.Status.OK).entity(new CustomResponse<>(responses, "Busqueda por tipos de alerta: " + tipoAlertas)).build();
    }


    @GET
    @Path( "/findAll" )
    public Response getAllAlerta()
    {
        List<AlertaDto> response;
        GetAllAlertaCommand command = null;
        //region Instrumentation DEBUG
        _logger.debug( "Get in AlertaService.getAlerta" );
        //endregion

        try
        {
            command = CommandFactory.createGetAllAlertaCommand();
            command.execute();
            if(command.getReturnParam() != null){
                response = AlertaMapper.mapListEntityToDto(command.getReturnParam());
            }else{
                return Response.status(Response.Status.OK).entity(new CustomResponse<>("No se puede Buscar por " )).build();
            }
        }
        catch ( Exception e )
        {
            return Response.status(Response.Status.OK).entity(new CustomResponse<>("Error en Alerta ")).build();

        }
        finally
        {
            if (command != null)
                command.closeHandlerSession();
        }

        _logger.debug( "Leaving AlertaService.getAlerta" );
        return Response.status(Response.Status.OK).entity(new CustomResponse<>(response,"Busqueda por Id Alerta: " )).build();
    }

    @POST
    @Path("/insert")
    public Response addAlerta( AlertaDto alertaDto )
    {
        Alerta entity;
        AlertaDto response;
        CreateAlertaCommand command = null;
        //region Instrumentation DEBUG
        _logger.debug( "Get in AlertaService.addAlerta" );
        //endregion

        try
        {
            entity = AlertaMapper.mapDtoToEntityInsert( alertaDto );
            command = CommandFactory.createCreateAlertaCommand( entity );
            command.execute();
            if(command.getReturnParam() != null){
                response = AlertaMapper.mapEntityToDto(command.getReturnParam());
            }else{
                return Response.status(Response.Status.OK).entity(new CustomResponse<>("No se puede Insertar " + alertaDto.getId())).build();
            }
        }
        catch ( Exception e )
        {
            return Response.status(Response.Status.OK).entity(new CustomResponse<>("Error en Alerta " + alertaDto.getId())).build();

        }
        finally
        {
            if (command != null)
                command.closeHandlerSession();
        }

        _logger.debug( "Leaving AlertaService.addAlerta" );
        return Response.status(Response.Status.OK).entity(new CustomResponse<>(response,"Insertado: " + alertaDto.getId())).build();
    }

    @DELETE
    @Path("/delete")
    public Response deleteAlerta(AlertaDto userDto )
    {
        Alerta entity;
        AlertaDto response;
        DeleteAlertaCommand command = null;
        //region Instrumentation DEBUG
        _logger.debug( "Get in AlertaService.deleteAlerta" );
        //endregion

        try
        {
            entity = AlertaMapper.mapDtoToEntity( userDto );
            command = CommandFactory.createDeleteAlertaCommand( entity );
            command.execute();
            if(command.getReturnParam() != null){
                response = AlertaMapper.mapEntityToDto(command.getReturnParam());
            }else{
                return Response.status(Response.Status.OK).entity(new CustomResponse<>("No se puede eliminar " + userDto.getId())).build();
            }

        }
        catch ( Exception e )
        {
            return Response.status(Response.Status.OK).entity(new CustomResponse<>("Error en Alerta " + userDto.getId())).build();

        }
        finally
        {
            if (command != null)
                command.closeHandlerSession();
        }

        _logger.debug( "Leaving AlertaService.deleteAlerta" );
        return Response.status(Response.Status.OK).entity(new CustomResponse<>(response,"Eliminado: " + userDto.getId())).build();
    }

    @PUT
    @Path("/delete2")
    public Response deleteAlerta2 (AlertaDto alertaDto){
        Alerta entity;
        AlertaDto responseDto = null;
        UpdateAlertaCommand command = null;
        AlertaDao base = new AlertaDao();

        try{
            if (base.find(alertaDto.getId(),Alerta.class) == null){
                return Response.status(Response.Status.OK).entity(new CustomResponse<>("No se encuentra el Objeto registrado " + alertaDto.getId())).build();

            }
            entity = AlertaMapper.mapDtoToEntity(alertaDto);
            command = CommandFactory.createUpdateAlertaCommand(entity);
            command.execute();

            if(command.getReturnParam() != null){
                responseDto = AlertaMapper.mapEntityToDto(command.getReturnParam());
            }else{
                return Response.status(Response.Status.OK).entity(new CustomResponse<>("No se puede eliminar " + alertaDto.getId())).build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.OK).entity(new CustomResponse<>("Error en Alerta " + alertaDto.getId())).build();
        } finally {
            if(command != null){
                command.closeHandlerSession();
            }
        }

        return Response.status(Response.Status.OK).entity(new CustomResponse<>(responseDto,"Eliminado: " + alertaDto.getId())).build();

    }
    @PUT
    @Path("/update")
    public Response updateAlerta (AlertaDto alertaDto){
        Alerta entity;
        AlertaDto responseDto = null;
        UpdateAlertaCommand command = null;
        AlertaDao base = new AlertaDao();

        try{
            if (base.find(alertaDto.getId(),Alerta.class) == null){
                return Response.status(Response.Status.OK).entity(new CustomResponse<>("No se encuentra el Objeto registrado " + alertaDto.getId())).build();

            }
            entity = AlertaMapper.mapDtoToEntity(alertaDto);
            command = CommandFactory.createUpdateAlertaCommand(entity);
            command.execute();

            if(command.getReturnParam() != null){
                responseDto = AlertaMapper.mapEntityToDto(command.getReturnParam());
            }else{
                return Response.status(Response.Status.OK).entity(new CustomResponse<>("No se puede editar " + alertaDto.getId())).build();
            }

        } catch (Exception e) {
            return Response.status(Response.Status.OK).entity(new CustomResponse<>("Error en Alerta " + alertaDto.getId())).build();
        } finally {
            if(command != null){
                command.closeHandlerSession();
            }
        }

        return Response.status(Response.Status.OK).entity(new CustomResponse<>(responseDto,"Editado: " + alertaDto.getId())).build();

    }


}

