package gpyo.gui.model;

import gpyo.persistence.entity.admin.CostesReales;
import gpyo.persistence.entity.admin.Gasto;
import gpyo.service.businesslogic.IObraService;
import gpyo.service.businesslogic.ITesoreriaGastoService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@ManagedBean
@ViewScoped
@Controller
public class CostesRealesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9027490902407777920L;
	
	private List<CostesReales> costesReales = new ArrayList<CostesReales>();
	private List<CostesReales> previsionesReales = new ArrayList<CostesReales>();
	private int year;
	private List<String> opciones = new ArrayList<String>();
	@Autowired
	private IObraService obraService;
	@Autowired
	private ITesoreriaGastoService tesoreriaGastoService;
	/**
	 * Tipo de costes que se van a ver: directos, indirectos, estructurales. Incluye también cada uno de los tipos de estructurales.
	 */
	private String opcion;
	
	public void verCostesReales(){
		costesReales = new ArrayList<CostesReales>();
		for(int i=0;i<13;i++)
			costesReales.add(new CostesReales());
		List<String> meses = new ArrayList<String>();
		meses = inicializaMeses();
		for(int i=0;i<meses.size();i++)
			costesReales.get(i).setMes(meses.get(i));
		List<Gasto> gastos = new ArrayList<Gasto>();
		gastos = tesoreriaGastoService.getGastoYear(Integer.toString(year));
		String prevision = "Real";
		if(opcion.equals("Totales(CD+CI) todo"))
			calcularCostes(gastos, prevision);
		else{
			if(opcion.equals("CD todo")){
				for(int i=0;i<gastos.size();i++)
					gastos.get(i).setCantidad(gastos.get(i).getCantidad()*(gastos.get(i).getPerCosteDirecto()/100));
			}
			else{
				if(opcion.equals("CI todo"))
					for(int i=0;i<gastos.size();i++)
						gastos.get(i).setCantidad(gastos.get(i).getCantidad()*(gastos.get(i).getPerCosteIndirecto()/100));
				else{
					if(opcion.equals("Estructurales")){
						for(int i=0;i<gastos.size();i++)
							if(gastos.get(i).getTipoCosteEstructural().equals(""))
								gastos.get(i).setCantidad(0);
					}
					else{
						for(int i=0;i<gastos.size();i++)
							if(!gastos.get(i).getTipoCosteEstructural().equals(opcion))
								gastos.get(i).setCantidad(0);
					}
				}
			}
			calcularCostes(gastos, prevision);
		}
	}
	
	public void verPrevisionesReales(){
		previsionesReales = new ArrayList<CostesReales>();
		for(int i=0;i<13;i++)
			previsionesReales.add(new CostesReales());
		List<String> meses = new ArrayList<String>();
		meses = inicializaMeses();
		for(int i=0;i<meses.size();i++)
			previsionesReales.get(i).setMes(meses.get(i));
		List<Gasto> gastos = new ArrayList<Gasto>();
		gastos = tesoreriaGastoService.getGastoYear(Integer.toString(year));
		String prevision = "Previsión";
		if(opcion.equals("Totales(CD+CI) todo"))
			calcularCostes(gastos, prevision);
		else{
			if(opcion.equals("CD todo")){
				for(int i=0;i<gastos.size();i++)
					gastos.get(i).setCantidad(gastos.get(i).getCantidad()*(gastos.get(i).getPerCosteDirecto()/100));
			}
			else{
				if(opcion.equals("CI todo"))
					for(int i=0;i<gastos.size();i++)
						gastos.get(i).setCantidad(gastos.get(i).getCantidad()*(gastos.get(i).getPerCosteIndirecto()/100));
				else{
					if(opcion.equals("Estructurales")){
						for(int i=0;i<gastos.size();i++)
							if(gastos.get(i).getTipoCosteEstructural().equals(""))
								gastos.get(i).setCantidad(0);
					}
					else{
						for(int i=0;i<gastos.size();i++)
							if(!gastos.get(i).getTipoCosteEstructural().equals(opcion))
								gastos.get(i).setCantidad(0);
					}
				}
			}
			calcularCostes(gastos, prevision);
		}
	}
	
	public List<List<Float>> calcularCostes(List<Gasto> gastos, String prevision){
		List<List<Float>> gastosCalculados = new ArrayList<List<Float>>();
		List<Float> gastosPers = new ArrayList<Float>();
		List<Float> gastosGtos = new ArrayList<Float>();
		List<Float> gastosSum = new ArrayList<Float>();
		List<Float> gastosVeh = new ArrayList<Float>();
		List<Float> gastosTfno = new ArrayList<Float>();
		List<Float> gastosMens = new ArrayList<Float>();
		List<Float> gastosServProf = new ArrayList<Float>();
		List<Float> gastosMatOf = new ArrayList<Float>();
		List<Float> gastosMob = new ArrayList<Float>();
		List<Float> gastosInfor = new ArrayList<Float>();
		List<Float> gastosSeg = new ArrayList<Float>();
		List<Float> gastosAses = new ArrayList<Float>();
		List<Float> gastosUbu = new ArrayList<Float>();
		List<Float> gastosBcos = new ArrayList<Float>();
		List<Float> gastosVarios = new ArrayList<Float>();
		List<Float> gastosAlq = new ArrayList<Float>();
		List<Float> gastosPtamo = new ArrayList<Float>();
		List<Float> gastosLibr = new ArrayList<Float>();
		List<Float> gastosAcal = new ArrayList<Float>();
		List<Float> gastosRepr = new ArrayList<Float>();
		List<Float> gastosForm = new ArrayList<Float>();
		List<Float> gastosLimp = new ArrayList<Float>();
		List<Float> gastosResumen = new ArrayList<Float>();
		List<Float> gastosGlobal = new ArrayList<Float>();
		List<Float> gastosFacturacion = new ArrayList<Float>();
		List<Float> gastosGtosPeru = new ArrayList<Float>();
		for(int i=0;i<13;i++){
			gastosPers.add((float) 0);
			gastosGtos.add((float) 0);
			gastosSum.add((float) 0);
			gastosVeh.add((float) 0);
			gastosTfno.add((float) 0);
			gastosMens.add((float) 0);
			gastosServProf.add((float) 0);
			gastosMatOf.add((float) 0);
			gastosMob.add((float) 0);
			gastosInfor.add((float) 0);
			gastosSeg.add((float) 0);
			gastosAses.add((float) 0);
			gastosUbu.add((float) 0);
			gastosBcos.add((float) 0);
			gastosVarios.add((float) 0);
			gastosAlq.add((float) 0);
			gastosPtamo.add((float) 0);
			gastosLibr.add((float) 0);
			gastosAcal.add((float) 0);
			gastosRepr.add((float) 0);
			gastosForm.add((float) 0);
			gastosLimp.add((float) 0);
			gastosResumen.add((float) 0);
			gastosGlobal.add((float) 0);
			gastosFacturacion.add((float) 0);
			gastosGtosPeru.add((float) 0);
		}
		for(Gasto g:gastos){
			if(prevision.equals(g.getPrevision())){
			if(g.getCat().equals("PERS")){
				gastosPers = colocarCosteEnMes(g, gastosPers, prevision);
			}
			else
				if(g.getCat().equals("GTOS"))
					gastosGtos = colocarCosteEnMes(g, gastosGtos, prevision);
				else
					if(g.getCat().equals("SUM"))
						gastosSum = colocarCosteEnMes(g, gastosSum, prevision);
					else
						if(g.getCat().equals("VEH"))
							gastosVeh = colocarCosteEnMes(g, gastosVeh, prevision);
						else
							if(g.getCat().equals("TFNO"))
								gastosTfno = colocarCosteEnMes(g, gastosTfno, prevision);
							else
								if(g.getCat().equals("MENS"))
									gastosMens = colocarCosteEnMes(g, gastosMens, prevision);
								else
									if(g.getCat().equals("SERV. PROF"))
										gastosServProf = colocarCosteEnMes(g, gastosServProf, prevision);
									else
										if(g.getCat().equals("MAT OF"))
											gastosMatOf = colocarCosteEnMes(g, gastosMatOf, prevision);
										else
											if(g.getCat().equals("MOB"))
												gastosMob = colocarCosteEnMes(g, gastosMob, prevision);
											else
												if(g.getCat().equals("INFOR"))
													gastosInfor = colocarCosteEnMes(g, gastosInfor, prevision);
												else
													if(g.getCat().equals("SEG"))
														gastosSeg = colocarCosteEnMes(g, gastosSeg, prevision);
													else
														if(g.getCat().equals("ASES"))
															gastosAses = colocarCosteEnMes(g, gastosAses, prevision);
														else
															if(g.getCat().equals("UBU"))
																gastosUbu = colocarCosteEnMes(g, gastosUbu, prevision);
															else
																if(g.getCat().equals("BCOS"))
																	gastosBcos  = colocarCosteEnMes(g, gastosBcos, prevision);
																else
																	if(g.getCat().equals("VARIOS"))
																		gastosVarios = colocarCosteEnMes(g, gastosVarios, prevision);
																	else
																		if(g.getCat().equals("ALQ"))
																			gastosAlq = colocarCosteEnMes(g, gastosAlq, prevision);
																		else
																			if(g.getCat().equals("PTAMO"))
																				gastosPtamo = colocarCosteEnMes(g, gastosPtamo, prevision);
																			else
																				if(g.getCat().equals("LIBR"))
																					gastosLibr = colocarCosteEnMes(g, gastosLibr, prevision);
																				else
																					if(g.getCat().equals("ACAL"))
																						gastosAcal = colocarCosteEnMes(g, gastosAcal, prevision);
																					else
																						if(g.getCat().equals("REPR"))
																							gastosRepr = colocarCosteEnMes(g, gastosRepr, prevision);
																						else
																							if(g.getCat().equals("FORM"))
																								gastosForm = colocarCosteEnMes(g, gastosForm, prevision);
																							else
																								if(g.getCat().equals("LIMP"))
																									gastosLimp = colocarCosteEnMes(g, gastosLimp, prevision);
																								else
																									if(g.getCat().equals("RESUMEN"))
																										gastosResumen = colocarCosteEnMes(g, gastosResumen, prevision);
																									else
																										if(g.getCat().equals("GLOBAL"))
																											gastosGlobal = colocarCosteEnMes(g, gastosGlobal, prevision);
																										else
																											if(g.getCat().equals("FACTURACION"))
																												gastosFacturacion = colocarCosteEnMes(g, gastosFacturacion, prevision);
																											else
																												if(g.getCat().equals("GTOS PERU"))
																													gastosGtosPeru = colocarCosteEnMes(g, gastosGtosPeru, prevision);
		}
		}
		if(prevision.equals("Real")){
			for(int i=0;i<12;i++){
				costesReales.get(i).setAcal(gastosAcal.get(i));
				costesReales.get(i).setAlq(gastosAlq.get(i));
				costesReales.get(i).setAses(gastosAses.get(i));
				costesReales.get(i).setBcos(gastosBcos.get(i));
				costesReales.get(i).setFacturacion(gastosFacturacion.get(i));
				costesReales.get(i).setForm(gastosForm.get(i));
				costesReales.get(i).setGlobal(gastosGlobal.get(i));
				costesReales.get(i).setGtos(gastosGtos.get(i));
				costesReales.get(i).setGtosPeru(gastosGtosPeru.get(i));
				costesReales.get(i).setInfor(gastosInfor.get(i));
				costesReales.get(i).setLibr(gastosLibr.get(i));
				costesReales.get(i).setLimp(gastosLimp.get(i));
				costesReales.get(i).setMatOf(gastosMatOf.get(i));
				costesReales.get(i).setMens(gastosMens.get(i));
				costesReales.get(i).setMob(gastosMob.get(i));
				costesReales.get(i).setPers(gastosPers.get(i));
				costesReales.get(i).setPtamo(gastosPtamo.get(i));
				costesReales.get(i).setRepr(gastosRepr.get(i));
				costesReales.get(i).setResumen(gastosResumen.get(i));
				costesReales.get(i).setSeg(gastosSeg.get(i));
				costesReales.get(i).setServProf(gastosServProf.get(i));
				costesReales.get(i).setSum(gastosSum.get(i));
				costesReales.get(i).setTfno(gastosTfno.get(i));
				costesReales.get(i).setUbu(gastosUbu.get(i));
				costesReales.get(i).setVarios(gastosVarios.get(i));
				costesReales.get(i).setVeh(gastosVeh.get(i));
			}
			for(int i=0;i<12;i++){
				costesReales.get(12).setAcal(costesReales.get(12).getAcal()+gastosAcal.get(i));
				costesReales.get(12).setAlq(costesReales.get(12).getAlq()+gastosAlq.get(i));
				costesReales.get(12).setAses(costesReales.get(12).getAses()+gastosAses.get(i));
				costesReales.get(12).setBcos(costesReales.get(12).getBcos()+gastosBcos.get(i));
				costesReales.get(12).setFacturacion(costesReales.get(12).getFacturacion()+gastosFacturacion.get(i));
				costesReales.get(12).setForm(costesReales.get(12).getForm()+gastosForm.get(i));
				costesReales.get(12).setGlobal(costesReales.get(12).getGlobal()+gastosGlobal.get(i));
				costesReales.get(12).setGtos(costesReales.get(12).getGtos()+gastosGtos.get(i));
				costesReales.get(12).setGtosPeru(costesReales.get(12).getGtosPeru()+gastosGtosPeru.get(i));
				costesReales.get(12).setInfor(costesReales.get(12).getInfor()+gastosInfor.get(i));
				costesReales.get(12).setLibr(costesReales.get(12).getLibr()+gastosLibr.get(i));
				costesReales.get(12).setLimp(costesReales.get(12).getLimp()+gastosLimp.get(i));
				costesReales.get(12).setMatOf(costesReales.get(12).getMatOf()+gastosMatOf.get(i));
				costesReales.get(12).setMens(costesReales.get(12).getMens()+gastosMens.get(i));
				costesReales.get(12).setMob(costesReales.get(12).getMob()+gastosMob.get(i));
				costesReales.get(12).setPers(costesReales.get(12).getPers()+gastosPers.get(i));
				costesReales.get(12).setPtamo(costesReales.get(12).getPtamo()+gastosPtamo.get(i));
				costesReales.get(12).setRepr(costesReales.get(12).getRepr()+gastosRepr.get(i));
				costesReales.get(12).setResumen(costesReales.get(12).getResumen()+gastosResumen.get(i));
				costesReales.get(12).setSeg(costesReales.get(12).getSeg()+gastosSeg.get(i));
				costesReales.get(12).setServProf(costesReales.get(12).getServProf()+gastosServProf.get(i));
				costesReales.get(12).setSum(costesReales.get(12).getSum()+gastosSum.get(i));
				costesReales.get(12).setTfno(costesReales.get(12).getTfno()+gastosTfno.get(i));
				costesReales.get(12).setUbu(costesReales.get(12).getUbu()+gastosUbu.get(i));
				costesReales.get(12).setVarios(costesReales.get(12).getVarios()+gastosVarios.get(i));
				costesReales.get(12).setVeh(costesReales.get(12).getVeh()+gastosVeh.get(i));
			}
		}
		else{
			for(int i=0;i<12;i++){
				previsionesReales.get(i).setAcal(gastosAcal.get(i));
				previsionesReales.get(i).setAlq(gastosAlq.get(i));
				previsionesReales.get(i).setAses(gastosAses.get(i));
				previsionesReales.get(i).setBcos(gastosBcos.get(i));
				previsionesReales.get(i).setFacturacion(gastosFacturacion.get(i));
				previsionesReales.get(i).setForm(gastosForm.get(i));
				previsionesReales.get(i).setGlobal(gastosGlobal.get(i));
				previsionesReales.get(i).setGtos(gastosGtos.get(i));
				previsionesReales.get(i).setGtosPeru(gastosGtosPeru.get(i));
				previsionesReales.get(i).setInfor(gastosInfor.get(i));
				previsionesReales.get(i).setLibr(gastosLibr.get(i));
				previsionesReales.get(i).setLimp(gastosLimp.get(i));
				previsionesReales.get(i).setMatOf(gastosMatOf.get(i));
				previsionesReales.get(i).setMens(gastosMens.get(i));
				previsionesReales.get(i).setMob(gastosMob.get(i));
				previsionesReales.get(i).setPers(gastosPers.get(i));
				previsionesReales.get(i).setPtamo(gastosPtamo.get(i));
				previsionesReales.get(i).setRepr(gastosRepr.get(i));
				previsionesReales.get(i).setResumen(gastosResumen.get(i));
				previsionesReales.get(i).setSeg(gastosSeg.get(i));
				previsionesReales.get(i).setServProf(gastosServProf.get(i));
				previsionesReales.get(i).setSum(gastosSum.get(i));
				previsionesReales.get(i).setTfno(gastosTfno.get(i));
				previsionesReales.get(i).setUbu(gastosUbu.get(i));
				previsionesReales.get(i).setVarios(gastosVarios.get(i));
				previsionesReales.get(i).setVeh(gastosVeh.get(i));
			}
			for(int i=0;i<12;i++){
				previsionesReales.get(12).setAcal(previsionesReales.get(12).getAcal()+gastosAcal.get(i));
				previsionesReales.get(12).setAlq(previsionesReales.get(12).getAlq()+gastosAlq.get(i));
				previsionesReales.get(12).setAses(previsionesReales.get(12).getAses()+gastosAses.get(i));
				previsionesReales.get(12).setBcos(previsionesReales.get(12).getBcos()+gastosBcos.get(i));
				previsionesReales.get(12).setFacturacion(previsionesReales.get(12).getFacturacion()+gastosFacturacion.get(i));
				previsionesReales.get(12).setForm(previsionesReales.get(12).getForm()+gastosForm.get(i));
				previsionesReales.get(12).setGlobal(previsionesReales.get(12).getGlobal()+gastosGlobal.get(i));
				previsionesReales.get(12).setGtos(previsionesReales.get(12).getGtos()+gastosGtos.get(i));
				previsionesReales.get(12).setGtosPeru(previsionesReales.get(12).getGtosPeru()+gastosGtosPeru.get(i));
				previsionesReales.get(12).setInfor(previsionesReales.get(12).getInfor()+gastosInfor.get(i));
				previsionesReales.get(12).setLibr(previsionesReales.get(12).getLibr()+gastosLibr.get(i));
				previsionesReales.get(12).setLimp(previsionesReales.get(12).getLimp()+gastosLimp.get(i));
				previsionesReales.get(12).setMatOf(previsionesReales.get(12).getMatOf()+gastosMatOf.get(i));
				previsionesReales.get(12).setMens(previsionesReales.get(12).getMens()+gastosMens.get(i));
				previsionesReales.get(12).setMob(previsionesReales.get(12).getMob()+gastosMob.get(i));
				previsionesReales.get(12).setPers(previsionesReales.get(12).getPers()+gastosPers.get(i));
				previsionesReales.get(12).setPtamo(previsionesReales.get(12).getPtamo()+gastosPtamo.get(i));
				previsionesReales.get(12).setRepr(previsionesReales.get(12).getRepr()+gastosRepr.get(i));
				previsionesReales.get(12).setResumen(previsionesReales.get(12).getResumen()+gastosResumen.get(i));
				previsionesReales.get(12).setSeg(previsionesReales.get(12).getSeg()+gastosSeg.get(i));
				previsionesReales.get(12).setServProf(previsionesReales.get(12).getServProf()+gastosServProf.get(i));
				previsionesReales.get(12).setSum(previsionesReales.get(12).getSum()+gastosSum.get(i));
				previsionesReales.get(12).setTfno(previsionesReales.get(12).getTfno()+gastosTfno.get(i));
				previsionesReales.get(12).setUbu(previsionesReales.get(12).getUbu()+gastosUbu.get(i));
				previsionesReales.get(12).setVarios(previsionesReales.get(12).getVarios()+gastosVarios.get(i));
				previsionesReales.get(12).setVeh(previsionesReales.get(12).getVeh()+gastosVeh.get(i));
			}
		}
		return gastosCalculados;
	}
	
	public List<Float> colocarCosteEnMes(Gasto gasto, List<Float> gastos, String prevision){
		String mes=new StringBuilder().append(gasto.getFecha().charAt(3)).append(gasto.getFecha().charAt(4)).toString();
		if(prevision.equals("Real")){
			if(mes.equals("01"))
				gastos.set(0, gasto.getCantidad()+gastos.get(0));
			else{
				if(mes.equals("02"))
					gastos.set(1, gasto.getCantidad()+gastos.get(1));
				else{
					if(mes.equals("03"))
						gastos.set(2, gasto.getCantidad()+gastos.get(2));
					else{
						if(mes.equals("04"))
							gastos.set(3, gasto.getCantidad()+gastos.get(3));
						else{
							if(mes.equals("05"))
								gastos.set(4, gasto.getCantidad()+gastos.get(4));
							else{
								if(mes.equals("06"))
									gastos.set(5, gasto.getCantidad()+gastos.get(5));
								else{
									if(mes.equals("07"))
										gastos.set(6, gasto.getCantidad()+gastos.get(6));
									else{
										if(mes.equals("08"))
											gastos.set(7, gasto.getCantidad()+gastos.get(7));
										else{
											if(mes.equals("09"))
												gastos.set(8, gasto.getCantidad()+gastos.get(8));
											else{
												if(mes.equals("10"))
													gastos.set(9, gasto.getCantidad()+gastos.get(9));
												else{
													if(mes.equals("11"))
														gastos.set(10, gasto.getCantidad()+gastos.get(10));
													else{
														if(mes.equals("12"))
															gastos.set(11, gasto.getCantidad()+gastos.get(11));
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		else
			return colocarPrevisionCosteEnMes(gasto, gastos, prevision);
		return gastos;
	}
	
	public List<Float> colocarPrevisionCosteEnMes(Gasto gasto, List<Float> gastos, String prevision){
		String mes=new StringBuilder().append(gasto.getFecha().charAt(3)).append(gasto.getFecha().charAt(4)).toString();
		if(mes.equals("01"))
			gastos.set(0, gasto.getCantidad()+gastos.get(0));
		else{
			if(mes.equals("02"))
				gastos.set(1, gasto.getCantidad()+gastos.get(1));
			else{
				if(mes.equals("03"))
					gastos.set(2, gasto.getCantidad()+gastos.get(2));
				else{
					if(mes.equals("04"))
						gastos.set(3, gasto.getCantidad()+gastos.get(3));
					else{
						if(mes.equals("05"))
							gastos.set(4, gasto.getCantidad()+gastos.get(4));
						else{
							if(mes.equals("06"))
								gastos.set(5, gasto.getCantidad()+gastos.get(5));
							else{
								if(mes.equals("07"))
									gastos.set(6, gasto.getCantidad()+gastos.get(6));
								else{
									if(mes.equals("08"))
										gastos.set(7, gasto.getCantidad()+gastos.get(7));
									else{
										if(mes.equals("09"))
											gastos.set(8, gasto.getCantidad()+gastos.get(8));
										else{
											if(mes.equals("10"))
												gastos.set(9, gasto.getCantidad()+gastos.get(9));
											else{
												if(mes.equals("11"))
													gastos.set(10, gasto.getCantidad()+gastos.get(10));
												else{
													if(mes.equals("12"))
														gastos.set(11, gasto.getCantidad()+gastos.get(11));
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return gastos;
	}
	
	public List<String> inicializaMeses(){
		List<String> meses = new ArrayList<String>();
		meses.add("ENERO");
		meses.add("FEBRERO");
		meses.add("MARZO");
		meses.add("ABRIL");
		meses.add("MAYO");
		meses.add("JUNIO");
		meses.add("JULIO");
		meses.add("AGOSTO");
		meses.add("SEPTIEMBRE");
		meses.add("OCTUBRE");
		meses.add("NOVIEMBRE");
		meses.add("DICIEMBRE");
		meses.add("TOTALES");
		return meses;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the costesReales
	 */
	public List<CostesReales> getCostesReales() {
		return costesReales;
	}

	/**
	 * @param costesReales the costesReales to set
	 */
	public void setCostesReales(List<CostesReales> costesReales) {
		this.costesReales = costesReales;
	}

	/**
	 * @return the opcion
	 */
	public String getOpcion() {
		return opcion;
	}

	/**
	 * @param opcion the opcion to set
	 */
	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public List<String> getOpciones() {
		opciones = new ArrayList<String>();
		opciones.add("Totales(CD+CI) todo");
		opciones.add("CD todo");
		opciones.add("CI todo");
		opciones.add("Estructurales");
		opciones.add("Estudios");
		opciones.add("Calidad");
		opciones.add("Comercial");
		opciones.add("SG");
		opciones.add("Internacional");
		opciones.add("CC115");
		opciones.add("I+D");
		opciones.add("Vinoteca");
		return opciones;
	}

	public void setOpciones(List<String> opciones) {
		this.opciones = opciones;
	}

	/**
	 * @return the obraService
	 */
	public IObraService getObraService() {
		return obraService;
	}

	/**
	 * @param obraService the obraService to set
	 */
	public void setObraService(IObraService obraService) {
		this.obraService = obraService;
	}

	public ITesoreriaGastoService getTesoreriaGastoService() {
		return tesoreriaGastoService;
	}

	public void setTesoreriaGastoService(
			ITesoreriaGastoService tesoreriaGastoService) {
		this.tesoreriaGastoService = tesoreriaGastoService;
	}

	/**
	 * @return the previsionesReales
	 */
	public List<CostesReales> getPrevisionesReales() {
		return previsionesReales;
	}

	/**
	 * @param previsionesReales the previsionesReales to set
	 */
	public void setPrevisionesReales(List<CostesReales> previsionesReales) {
		this.previsionesReales = previsionesReales;
	}

}
