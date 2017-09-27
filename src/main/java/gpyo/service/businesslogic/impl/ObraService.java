package gpyo.service.businesslogic.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gpyo.persistence.dao.IObraDao;
import gpyo.persistence.dao.IObraDeUsuarioDao;
import gpyo.persistence.dao.IObraDeUsuarioHistoricoDao;
import gpyo.persistence.dao.IUsuarioDao;
import gpyo.persistence.entity.admin.Obra;
import gpyo.persistence.entity.admin.ObraDeUsuario;
import gpyo.persistence.entity.admin.ObraDeUsuarioHistorica;
import gpyo.persistence.entity.admin.Usuario;
import gpyo.service.businesslogic.IObraService;

/**
 * Servicio para conectar con el DAO de obras, obras de usuario y obras de usuario históricas.
 * @author Alberto Revilla Gil.
 * @version 1.0
 */
@Service
public class ObraService implements IObraService{

	@Autowired
	private IObraDao obraDao;
	@Autowired
	private IObraDeUsuarioDao obraDeUsuarioDao;
	@Autowired
	private IObraDeUsuarioHistoricoDao obraDeUsuarioHistoricoDao;
	@Autowired
	private IUsuarioDao usuarioDao;
	
	public IObraDao getObraDao() {
		return obraDao;
	}

	public void setObraDao(IObraDao obraDao) {
		this.obraDao = obraDao;
	}
	
	@Transactional
	public double horasDia(long idUsuario, String fecha){
		return obraDeUsuarioDao.horasDia(idUsuario, fecha);
	}

	@Transactional
	public List<Obra> getObras() {
		return obraDao.getObras();
	}
	
	@Transactional
	public List<Obra> getObrasCodigo() {
		return obraDao.getObrasCodigo();
	}
	
	@Transactional
	public List<Obra> getObrasTitulo() {
		return obraDao.getObrasTitulo();
	}

	/**
	 * @return the obrasDeUsuario
	 */
	@Transactional
	public List<ObraDeUsuario> getObrasDeUsuario(String fecha) {
		Usuario usuario=new Usuario();
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioDao.getUsuarioPorNombre(nombreUsuario);
		return obraDeUsuarioDao.getObras(usuario.getId(), fecha);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasDeUsuario(long idUsuario, String fecha, String obra) {
		return obraDeUsuarioDao.getObras(idUsuario, fecha, obra);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasDeUsuario2(long idUsuario, String obra) {
		return obraDeUsuarioDao.getObras2(idUsuario, obra);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasDeUsuarioMes(long idUsuario, int code){
		return obraDeUsuarioDao.getObrasMes(idUsuario, code);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasDeUsuarioMesObra(long idUsuario, int code, String obra){
		return obraDeUsuarioDao.getObrasMesObra(idUsuario, code, obra);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorYearObra(int yearFilter, String nombreObraFilter){
		return obraDeUsuarioDao.getObrasPorYearObra(yearFilter, nombreObraFilter);
	}

	@Transactional
	public List<ObraDeUsuario> getObrasPorUsuarioYearMesObra(String usuario, int code, int year){
		return obraDeUsuarioDao.getObrasPorUsuarioYearMesObra(usuario, code, year);
	}
	
	public IObraDeUsuarioDao getObraDeUsuarioDao() {
		return obraDeUsuarioDao;
	}

	public void setObraDeUsuarioDao(IObraDeUsuarioDao obraDeUsuarioDao) {
		this.obraDeUsuarioDao = obraDeUsuarioDao;
	}

	
	@Transactional
	public void saveObraDeUsuario(ObraDeUsuario obra){
		Usuario usuario = new Usuario();
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioDao.getUsuarioPorNombre(nombreUsuario);
		obra.getIdObraDeUsuario().setUsuario(usuario);
		obraDeUsuarioDao.insertObraDeUsuario(obra);
	}

	@Transactional
	public Obra getObraByNombre(String nombre){
		return obraDao.getObra(nombre);
	}
	
	@Transactional
	public Obra getObraByCodigo(String codigo){
		return obraDao.getObraPorCodigo(codigo);
	}
	
	@Transactional
	public void deleteRatio(ObraDeUsuario obra){
		obraDeUsuarioDao.deleteObraUsuario(obra);
	}
	
	@Transactional
	public void updateRatio(ObraDeUsuario obraNueva){
		obraDeUsuarioDao.updateRatio(obraNueva);
	}
	
	@Transactional
	public List<ObraDeUsuario> getTodasObrasDeUsuario(){
		Usuario usuario=new Usuario();
		String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		usuario = usuarioDao.getUsuarioPorNombre(nombreUsuario);
		return obraDeUsuarioDao.getTodasObras(usuario.getId());
	}
	
	@Transactional
	public void saveObra(Obra obra){
		obraDao.insertObra(obra);
	}
	
	@Transactional
	public void deleteObra(Obra obra){
		obraDao.borrarObra(obra);
	}
	
	@Transactional
	public void updateObra(Obra obra){
		obraDao.updateObra(obra);
	}

	@Override
	@Transactional
	public List<Obra> getObras(String codigo) {
		return obraDao.getObras(codigo);
	}

	@Override
	@Transactional
	public List<Obra> getObras2(String obra) {
		return obraDao.getObras2(obra);
	}

	@Override
	@Transactional
	public List<Obra> getObrasPorTitulo(String titulo) {
		return obraDao.getObrasPorTitulo(titulo);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorFecha(String fecha){
		return obraDeUsuarioDao.getObrasPorFecha(fecha);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorNombre(String nombre){
		return obraDeUsuarioDao.getObrasPorNombre(nombre);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorMes(int code){
		return obraDeUsuarioDao.getObrasPorMes(code);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorCodigo(String codigo){
		return obraDeUsuarioDao.getObrasPorCodigo(codigo);
	}

	@Transactional
	public List<ObraDeUsuario> getObrasPorUsuario(String usuario){
		return obraDeUsuarioDao.getObrasPorUsuario(usuario);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorMesObra(int code, String nombreObra){
		return obraDeUsuarioDao.getObrasPorMesObra(code, nombreObra);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorUsuarioObra(String usuario, String nombreObra){
		return obraDeUsuarioDao.getObrasPorUsuarioObra(usuario, nombreObra);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorUsuarioMes(String usuario, int code){
		return obraDeUsuarioDao.getObrasPorUsuarioMes(usuario, code);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorUsuarioMesObra(String usuario, int code, String nombreObra){
		return obraDeUsuarioDao.getObrasPorUsuarioMesObra(usuario, code, nombreObra);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasDeUsuario(){
		return obraDeUsuarioDao.getObrasDeUsuario();
	}
	
	@Override
	@Transactional
	public List<ObraDeUsuario> getObrasDeUsuarioPorTitulo(String titulo) {
		return obraDeUsuarioDao.getObrasDeUsuarioPorTitulo(titulo);
	}
	
	@Transactional
	public void saveObraHistorico(ObraDeUsuarioHistorica obraHistorica){
		obraDeUsuarioHistoricoDao.saveObraHistorico(obraHistorica);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorYear(int yearFilter){
		return obraDeUsuarioDao.getObrasPorYear(yearFilter);
	}

	@Transactional
	public List<ObraDeUsuario> getObrasPorYearUsuario(int yearFilter, String idUsuario){
		return obraDeUsuarioDao.getObrasPorYearUsuario(yearFilter, idUsuario);
	}
	
	@Transactional
	public List<ObraDeUsuario> getObrasPorYearObraUsuario(int yearFilter, String idUsuario, String obra){
		return obraDeUsuarioDao.getObrasPorYearObraUsuario(yearFilter, idUsuario, obra);
	}
	/**
	 * @return the obraDeUsuarioHistoricoDao
	 */
	public IObraDeUsuarioHistoricoDao getObraDeUsuarioHistoricoDao() {
		return obraDeUsuarioHistoricoDao;
	}

	/**
	 * @param obraDeUsuarioHistoricoDao the obraDeUsuarioHistoricoDao to set
	 */
	public void setObraDeUsuarioHistoricoDao(IObraDeUsuarioHistoricoDao obraDeUsuarioHistoricoDao) {
		this.obraDeUsuarioHistoricoDao = obraDeUsuarioHistoricoDao;
	}
	
	public IUsuarioDao getUsuarioDao(){
		return usuarioDao;
	}
	
	public void setUsuarioDao(IUsuarioDao usuarioDao){
		this.usuarioDao = usuarioDao;
	}
	
	@Transactional
	public List<ObraDeUsuarioHistorica> getObrasHistorico(){
		return obraDeUsuarioHistoricoDao.getObrasHistorico();
	}
	
	@Transactional
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorObra(String nombreObra){
		return obraDeUsuarioHistoricoDao.getObrasHistoricoPorObra(nombreObra);
	}
	
	@Transactional
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorCodigo(String codigoObra){
		return obraDeUsuarioHistoricoDao.getObrasHistoricoPorCodigo(codigoObra);
	}
	
	@Transactional
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorTitulo(String titulo){
		return obraDeUsuarioHistoricoDao.getObrasHistoricoPorTitulo(titulo);
	}
	
	@Transactional
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorUsuario(String usuarioObra){
		return obraDeUsuarioHistoricoDao.getObrasHistoricoPorUsuario(usuarioObra);
	}
	
	@Transactional
	public List<ObraDeUsuarioHistorica> getObrasHistoricoPorYear(String year){
		return obraDeUsuarioHistoricoDao.getObrasHistoricoPorYear(year);
	}
}
