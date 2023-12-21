package com.ucab.cmcapp.logic.commands.admin.atomic;

import com.ucab.cmcapp.common.entities.Admin;
import com.ucab.cmcapp.logic.commands.Command;
import com.ucab.cmcapp.persistence.DBHandler;
import com.ucab.cmcapp.persistence.DaoFactory;
import com.ucab.cmcapp.persistence.dao.AdminDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetAdminByCorreoCommand extends Command<Admin> {
    private static Logger _logger = LoggerFactory.getLogger(GetAdminByCorreoCommand.class);
    private Admin _Admin;
    private AdminDao _dao;

    public GetAdminByCorreoCommand(Admin Admin) {
        //region Instrumentation DEBUG
        _logger.debug(String.format("Tomando de GetUsuarioByCorreoCommand.ctor: parameter {%s}", Admin.toString()));
        _Admin = Admin;
        setHandler(new DBHandler());
        _dao = DaoFactory.createAdminDao(getHandler());

        //region Instrumentation DEBUG
        _logger.debug(String.format("Dejando GetUsuarioByCorreoCommand.ctor: atribute {%s}", _Admin.toString()));
        //endregion
    }

    public GetAdminByCorreoCommand(Admin Admin, DBHandler handler) {
        //region Instrumentation DEBUG
        _logger.debug(String.format("Get in GetUsuarioByCorreoCommand.ctor: parameter {%s}", Admin.toString()));
        _Admin = Admin;
        setHandler(handler);
        _dao = DaoFactory.createAdminDao(getHandler());

        //region Instrumentation DEBUG
        _logger.debug(String.format("Dejando GetUsuarioByCorreoCommand.ctor: atribute {%s}", _Admin.toString()));
        //endregion
    }

    @Override
    public void execute() {
        //region Instrumentation DEBUG
        _logger.debug("Tomar de  GetUsuarioByCorreoCommand.execute");
        //endregion
        _Admin = _dao.getAdminByCorreo(_Admin.get_correo());
        //region Instrumentation DEBUG
        _logger.debug("Dejando  GetUsuarioByCorreoCommand.execute");
        //endregion
    }

    @Override
    public Admin getReturnParam() {
        return _Admin;
    }

    @Override
    public void closeHandlerSession() {
        getHandler().closeSession();
    }
}
