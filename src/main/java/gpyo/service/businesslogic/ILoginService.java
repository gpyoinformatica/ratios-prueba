package gpyo.service.businesslogic;

import gpyo.persistence.entity.admin.Usuario;

/**
 * Interfaz para el servicio de login de usuarios.
 * @author Alberto Revilla Gil
 * @version 1.0
 *
 */
public interface ILoginService {

	Usuario getByLoginInformation(String username, String password);

}
