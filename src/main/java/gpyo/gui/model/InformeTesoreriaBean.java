package gpyo.gui.model;

import gpyo.persistence.entity.admin.Gasto;
import gpyo.persistence.entity.admin.Ingreso;
import gpyo.persistence.entity.admin.Obra;
import gpyo.persistence.entity.admin.ObraDeUsuario;
import gpyo.persistence.entity.admin.Tesoreria;
import gpyo.service.businesslogic.IObraService;
import gpyo.service.businesslogic.ITesoreriaGastoService;
import gpyo.service.businesslogic.ITesoreriaIngresoService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@ManagedBean
@ViewScoped
@Controller
public class InformeTesoreriaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3035226635293317439L;

	private String nombreObra;
	private List<String> nombreObras = new ArrayList<String>();
	private String tipoInfo;
	private List<String> tipoInfos = new ArrayList<String>();
	private String tipoFecha;
	private List<String> tipoFechas = new ArrayList<String>();
	private String tipoFactura;
	private List<String> tipoFacturas = new ArrayList<String>();
	@Autowired
	private IObraService obraService;
	private List<Tesoreria> ingresos = new ArrayList<Tesoreria>();
	private List<Tesoreria> gastos = new ArrayList<Tesoreria>();
	@Autowired
	private ITesoreriaIngresoService tesoreriaIngresoService;
	@Autowired
	private ITesoreriaGastoService tesoreriaGastoService;
	private String year;
	private List<Tesoreria> gastosDesplegados = new ArrayList<Tesoreria>();
	private Tesoreria gastoDesplegable = new Tesoreria();
	
	private List<Tesoreria> gastosDespGeneral = new ArrayList<Tesoreria>();
	/**
	 * Las siguientes variables se utilizaban para el primer método de creación de los desplegables de gastos.
	 */
	/*private List<Tesoreria> gtoDesp = new ArrayList<Tesoreria>();
	private List<Tesoreria> tesoreriasDesplegables = new ArrayList<Tesoreria>();*/
	private Tesoreria tesoreriaSeleccionada;
	private List<Gasto> gastosDesplegables = new ArrayList<Gasto>();
	
	public void verTesoreria(){
		Obra obra = new Obra();
		ingresos = new ArrayList<Tesoreria>();
		gastos = new ArrayList<Tesoreria>();
		obra = obraService.getObraByNombre(nombreObra);
		List<Ingreso> ing = new ArrayList<Ingreso>();
		ing = tesoreriaIngresoService.getIngresosPorObra(obra.getIdObra());
		int j=0;
		for(Ingreso i : ing){
			calcularIngresos(i, j, tipoFecha);
			j++;
		}
		List<Gasto> gast = new ArrayList<Gasto>();
		gast = tesoreriaGastoService.getGastosPorObra(obra.getIdObra());
		/**Evita que coja los gastos de personal de los ratios*/
		/*Gasto gastoPersonal = new Gasto();
		gastoPersonal = crearGastoPersonal(nombreObra);
		calcularGastos(gastoPersonal, j, tipoFecha);*/
		j=0;
		for(int i=0;i<gast.size();i++)
			for(int x=gast.size()-1;x>0;x--)
				if(gast.get(i).getFactura().equals(gast.get(x).getFactura()) && i!=x)
					gast.remove(x);
		for(Gasto g : gast){
			calcularGastos(g, j, tipoFecha);
			j++;
		}
		for(int i=0;i<gastos.size();i++)
			gastos.get(i).setId_tesoreria(i);
		for(int i=0;i<gastos.size();i++){
			for(int x=gastos.size()-1;x>0;x--){
				if(gastos.get(i).getTitulos().equals(gastos.get(x).getTitulos()) && i!=x){
					gastos.get(i).setEnero(gastos.get(i).getEnero()+gastos.get(x).getEnero());
					gastos.get(i).setFebrero(gastos.get(i).getFebrero()+gastos.get(x).getFebrero());
					gastos.get(i).setMarzo(gastos.get(i).getMarzo()+gastos.get(x).getMarzo());
					gastos.get(i).setAbril(gastos.get(i).getAbril()+gastos.get(x).getAbril());
					gastos.get(i).setMayo(gastos.get(i).getMayo()+gastos.get(x).getMayo());
					gastos.get(i).setJunio(gastos.get(i).getJunio()+gastos.get(x).getJunio());
					gastos.get(i).setJulio(gastos.get(i).getJulio()+gastos.get(x).getJulio());
					gastos.get(i).setAgosto(gastos.get(i).getAgosto()+gastos.get(x).getAgosto());
					gastos.get(i).setSeptiembre(gastos.get(i).getSeptiembre()+gastos.get(x).getSeptiembre());
					gastos.get(i).setOctubre(gastos.get(i).getOctubre()+gastos.get(x).getOctubre());
					gastos.get(i).setNoviembre(gastos.get(i).getNoviembre()+gastos.get(x).getNoviembre());
					gastos.get(i).setDiciembre(gastos.get(i).getDiciembre()+gastos.get(x).getDiciembre());
					gastos.get(i).setTotal(gastos.get(i).getTotal()+gastos.get(x).getTotal());
					gastos.remove(x);
				}
			}
		}
		/**
		 * Deprecated. Código usado para crear los desplegables de gastos. Al final se hace de otra manera.
		 */
		/*for(int i=0;i<gastosDespGeneral.size();i++){
			if(gastosDespGeneral.get(i).getTitulos().equals("GTOS"))
				gtoDesp.add(gastosDespGeneral.get(i));
			tesoreriasDesplegables.add(gastosDespGeneral.get(i));
		}*/
	}
	
	/** 
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * Se tendrá que cambiar el número por el que se multiplican las horas en función del usuario.
	 * @param nombreObra
	 * @return
	 */
	public Gasto crearGastoPersonal(String nombreObra){
		Gasto gastoPersonal = new Gasto();
		List<ObraDeUsuario> ratios = new ArrayList<ObraDeUsuario>();
		ratios = obraService.getObrasPorNombre(nombreObra);
		gastoPersonal.setCat("PERS");
		for(int i=0;i<ratios.size();i++){
			if(year.equals(new StringBuilder().append(ratios.get(i).getFecha().charAt(6)).append(ratios.get(i).getFecha().charAt(7)).append(ratios.get(i).getFecha().charAt(8)).append(ratios.get(i).getFecha().charAt(9)).toString())){
				gastoPersonal.setBaseImponible(gastoPersonal.getBaseImponible()+(ratios.get(i).getHorasTecnico()*12)+(ratios.get(i).getHorasAdmin()*12));
				gastoPersonal.setFecha(ratios.get(i).getFecha());
				gastoPersonal.setFechaPago(gastoPersonal.getFecha());
			}
		}
		return gastoPersonal;
	}
	
	public void calcularGastos(Gasto g, int j, String opcion){
		Tesoreria gastosPers = new Tesoreria();
		Tesoreria gastosGtos = new Tesoreria();
		Tesoreria gastosSum = new Tesoreria();
		Tesoreria gastosVeh = new Tesoreria();
		Tesoreria gastosTfno = new Tesoreria();
		Tesoreria gastosMens = new Tesoreria();
		Tesoreria gastosServProf = new Tesoreria();
		Tesoreria gastosMatOf = new Tesoreria();
		Tesoreria gastosMob = new Tesoreria();
		Tesoreria gastosInfor = new Tesoreria();
		Tesoreria gastosSeg = new Tesoreria();
		Tesoreria gastosAses = new Tesoreria();
		Tesoreria gastosUbu = new Tesoreria();
		Tesoreria gastosBcos = new Tesoreria();
		Tesoreria gastosVarios = new Tesoreria();
		Tesoreria gastosAlq = new Tesoreria();
		Tesoreria gastosPtamo = new Tesoreria();
		Tesoreria gastosLibr = new Tesoreria();
		Tesoreria gastosAcal = new Tesoreria();
		Tesoreria gastosRepr = new Tesoreria();
		Tesoreria gastosForm = new Tesoreria();
		Tesoreria gastosLimp = new Tesoreria();
		Tesoreria gastosResumen = new Tesoreria();
		Tesoreria gastosGlobal = new Tesoreria();
		Tesoreria gastosFacturacion = new Tesoreria();
		Tesoreria gastosGtosPeru = new Tesoreria();
		if(g.getCat().equals("PERS")){
			g.setCantidad((float) (g.getBaseImponible()+(g.getBaseImponible()*0.21)));
			g.setVencimiento(g.getFecha());
			gastosPers = calcularGastosMes(g, gastosPers, opcion);
			gastos.add(gastosPers);
		}
		else
			if(g.getCat().equals("GTOS")){
				gastosGtos = calcularGastosMes(g, gastosGtos, opcion);
				gastos.add(gastosGtos);
			}
			else
				if(g.getCat().equals("SUM")){
					gastosSum = calcularGastosMes(g, gastosSum, opcion);
					gastos.add(gastosSum);
				}
				else
					if(g.getCat().equals("VEH")){
						gastosVeh = calcularGastosMes(g, gastosVeh, opcion);
						gastos.add(gastosVeh);
					}
					else
						if(g.getCat().equals("TFNO")){
							gastosTfno = calcularGastosMes(g, gastosTfno, opcion);
							gastos.add(gastosTfno);
						}
						else
							if(g.getCat().equals("MENS")){
								gastosMens = calcularGastosMes(g, gastosMens, opcion);
								gastos.add(gastosMens);
							}
							else
								if(g.getCat().equals("SERV. PROF")){
									gastosServProf = calcularGastosMes(g, gastosServProf, opcion);
									gastos.add(gastosServProf);
								}
								else
									if(g.getCat().equals("MAT OF")){
										gastosMatOf = calcularGastosMes(g, gastosMatOf, opcion);
										gastos.add(gastosMatOf);
									}
									else
										if(g.getCat().equals("MOB")){
											gastosMob = calcularGastosMes(g, gastosMob, opcion);
											gastos.add(gastosMob);
										}
										else
											if(g.getCat().equals("INFOR")){
												gastosInfor = calcularGastosMes(g, gastosInfor, opcion);
												gastos.add(gastosInfor);
											}
											else
												if(g.getCat().equals("SEG")){
													gastosSeg = calcularGastosMes(g, gastosSeg, opcion);
													gastos.add(gastosSeg);
												}
												else
													if(g.getCat().equals("ASES")){
														gastosAses = calcularGastosMes(g, gastosAses, opcion);
														gastos.add(gastosAses);
													}
													else
														if(g.getCat().equals("UBU")){
															gastosUbu = calcularGastosMes(g, gastosUbu, opcion);
															gastos.add(gastosUbu);
														}
														else
															if(g.getCat().equals("BCOS")){
																gastosBcos = calcularGastosMes(g, gastosBcos, opcion);
																gastos.add(gastosBcos);
															}
															else
																if(g.getCat().equals("VARIOS")){
																	gastosVarios = calcularGastosMes(g, gastosVarios, opcion);
																	gastos.add(gastosVarios);
																}
																else
																	if(g.getCat().equals("ALQ")){
																		gastosAlq = calcularGastosMes(g, gastosAlq, opcion);
																		gastos.add(gastosAlq);
																	}
																	else
																		if(g.getCat().equals("PTAMO")){
																			gastosPtamo = calcularGastosMes(g, gastosPtamo, opcion);
																			gastos.add(gastosPtamo);
																		}
																		else
																			if(g.getCat().equals("LIBR")){
																				gastosLibr = calcularGastosMes(g, gastosLibr, opcion);
																				gastos.add(gastosLibr);	
																			}
																			else
																				if(g.getCat().equals("ACAL")){
																					gastosAcal = calcularGastosMes(g, gastosAcal, opcion);
																					gastos.add(gastosAcal);	
																				}
																				else
																					if(g.getCat().equals("REPR")){
																						gastosRepr = calcularGastosMes(g, gastosRepr, opcion);
																						gastos.add(gastosRepr);	
																					}
																					else
																						if(g.getCat().equals("FORM")){
																							gastosForm = calcularGastosMes(g, gastosForm, opcion);
																							gastos.add(gastosForm);	
																						}
																						else
																							if(g.getCat().equals("LIMP")){
																								gastosLimp = calcularGastosMes(g, gastosLimp, opcion);
																								gastos.add(gastosLimp);	
																							}
																							else
																								if(g.getCat().equals("RESUMEN")){
																									gastosResumen = calcularGastosMes(g, gastosResumen, opcion);
																									gastos.add(gastosResumen);	
																								}
																								else
																									if(g.getCat().equals("GLOBAL")){
																										gastosGlobal = calcularGastosMes(g, gastosGlobal, opcion);
																										gastos.add(gastosGlobal);	
																									}
																									else
																										if(g.getCat().equals("FACTURACION")){
																											gastosFacturacion = calcularGastosMes(g, gastosFacturacion, opcion);
																											gastos.add(gastosFacturacion);	
																										}
																										else
																											if(g.getCat().equals("GTOS PERU")){
																												gastosGtosPeru = calcularGastosMes(g, gastosGtosPeru, opcion);
																												gastos.add(gastosGtosPeru);	
																											}
	}
	
	public Tesoreria calcularGastosMes(Gasto g, Tesoreria tes, String opcion){
		String mes = " ";
		String yearGasto = " ";
		if(opcion.equals("Fecha factura")){
			mes = new StringBuilder().append(g.getFecha().charAt(3)).append(g.getFecha().charAt(4)).toString();
			yearGasto = new StringBuilder().append(g.getFecha().charAt(6)).append(g.getFecha().charAt(7)).append(g.getFecha().charAt(8)).append(g.getFecha().charAt(9)).toString();
		}
		else{
			if(opcion.equals("Fecha vencimiento")){
				mes = new StringBuilder().append(g.getVencimiento().charAt(3)).append(g.getVencimiento().charAt(4)).toString();
				yearGasto = new StringBuilder().append(g.getVencimiento().charAt(6)).append(g.getVencimiento().charAt(7)).append(g.getVencimiento().charAt(8)).append(g.getVencimiento().charAt(9)).toString();
			}
			else
				if(g.getFechaPago() != null){
					mes = new StringBuilder().append(g.getFechaPago().charAt(3)).append(g.getFechaPago().charAt(4)).toString();
					yearGasto = new StringBuilder().append(g.getFechaPago().charAt(6)).append(g.getFechaPago().charAt(7)).append(g.getFechaPago().charAt(8)).append(g.getFechaPago().charAt(9)).toString();
				}
		}
		tes.setTitulos(g.getCat());
		float cantidad = 0;
		if(tipoInfo.equals("Base imponible"))
			cantidad = g.getBaseImponible();
		else
			cantidad = g.getCantidad();
		if(g.getPrevision() == null)
			g.setPrevision("Real");
		if(yearGasto.equals(year)/* && g.getPrevision().equals(tipoFactura)*/){
			if(mes.equals("01")){
				tes.setEnero(tes.getEnero()+cantidad);
				gastosDespGeneral.add(tes);
				if(g.getPrevision().equals("Real"))
					tes.setEneroPrev('R');
				else
					tes.setEneroPrev('P');
			}
			else
				if(mes.equals("02")){
					tes.setFebrero(tes.getFebrero()+cantidad);
					if(g.getPrevision().equals("Real"))
						tes.setFebreroPrev('R');
					else
						tes.setFebreroPrev('P');
				}
				else
					if(mes.equals("03")){
						tes.setMarzo(tes.getMarzo()+cantidad);
						if(g.getPrevision().equals("Real"))
							tes.setMarzoPrev('R');
						else
							tes.setMarzoPrev('P');
					}
					else
						if(mes.equals("04")){
							tes.setAbril(tes.getAbril()+cantidad);
							if(g.getPrevision().equals("Real"))
								tes.setAbrilPrev('R');
							else
								tes.setAbrilPrev('P');
						}
						else
							if(mes.equals("05")){
								tes.setMayo(tes.getMayo()+cantidad);
								if(g.getPrevision().equals("Real"))
									tes.setMayoPrev('R');
								else
									tes.setMayoPrev('P');
							}
							else
								if(mes.equals("06")){
									tes.setJunio(tes.getJunio()+cantidad);
									if(g.getPrevision().equals("Real"))
										tes.setJunioPrev('R');
									else
										tes.setJunioPrev('P');
								}
								else
									if(mes.equals("07")){
										tes.setJulio(tes.getJulio()+cantidad);
										if(g.getPrevision().equals("Real"))
											tes.setJulioPrev('R');
										else
											tes.setJulioPrev('P');
									}
									else
										if(mes.equals("08")){
											tes.setAgosto(tes.getAgosto()+cantidad);
											if(g.getPrevision().equals("Real"))
												tes.setAgostoPrev('R');
											else
												tes.setAgostoPrev('P');
										}
										else
											if(mes.equals("09")){
												tes.setSeptiembre(tes.getSeptiembre()+cantidad);
												if(g.getPrevision().equals("Real"))
													tes.setSeptiembrePrev('R');
												else
													tes.setSeptiembrePrev('P');
											}
											else
												if(mes.equals("10")){
													tes.setOctubre(tes.getOctubre()+cantidad);
													if(g.getPrevision().equals("Real"))
														tes.setOctubrePrev('R');
													else
														tes.setOctubrePrev('P');
												}
												else
													if(mes.equals("11")){
														tes.setNoviembre(tes.getNoviembre()+cantidad);
														if(g.getPrevision().equals("Real"))
															tes.setNoviembrePrev('R');
														else
															tes.setNoviembrePrev('P');
													}
													else
														if(mes.equals("12")){
															tes.setDiciembre(tes.getDiciembre()+cantidad);
															if(g.getPrevision().equals("Real"))
																tes.setDiciembrePrev('R');
															else
																tes.setDiciembrePrev('P');
														}
			tes.setTotal(tes.getTotal()+cantidad);
		}
		return tes;
	}
	
	public void calcularIngresos(Ingreso i, int j, String opcion){
		ingresos.add(new Tesoreria());
		String mes = " ";
		String yearIngreso = " ";
		if(opcion.equals("Fecha factura")){
			mes = new StringBuilder().append(i.getFecha().charAt(3)).append(i.getFecha().charAt(4)).toString();
			yearIngreso =  new StringBuilder().append(i.getFecha().charAt(6)).append(i.getFecha().charAt(7)).append(i.getFecha().charAt(8)).append(i.getFecha().charAt(9)).toString();
		}
		else{
			if(opcion.equals("Fecha vencimiento")){
				mes = new StringBuilder().append(i.getFechaVencimiento().charAt(3)).append(i.getFechaVencimiento().charAt(4)).toString();
				yearIngreso =  new StringBuilder().append(i.getFechaVencimiento().charAt(6)).append(i.getFechaVencimiento().charAt(7)).append(i.getFechaVencimiento().charAt(8)).append(i.getFechaVencimiento().charAt(9)).toString();
			}
			else
				if(i.getFechaCobro() != null){
					mes = new StringBuilder().append(i.getFechaCobro().charAt(3)).append(i.getFechaCobro().charAt(4)).toString();
					yearIngreso =  new StringBuilder().append(i.getFechaCobro().charAt(6)).append(i.getFechaCobro().charAt(7)).append(i.getFechaCobro().charAt(8)).append(i.getFechaCobro().charAt(9)).toString();
				}
		}
		ingresos.get(j).setTitulos(i.getOperacion());
		float cantidad = 0;
		if(tipoInfo.equals("Base imponible"))
			cantidad = i.getBaseImponible();
		else
			cantidad = i.getTotalIngreso();
		if(yearIngreso.equals(year)/* && i.getPrevision().equals(tipoFactura)*/){
			if(mes.equals("01")){
				ingresos.get(j).setEnero(ingresos.get(j).getEnero()+cantidad);
				if(i.getPrevision().equals("Real"))
					ingresos.get(j).setEneroPrev('R');
				else
					ingresos.get(j).setEneroPrev('P');
			}
			else
				if(mes.equals("02")){
					ingresos.get(j).setFebrero(ingresos.get(j).getFebrero()+cantidad);
					if(i.getPrevision().equals("Real"))
						ingresos.get(j).setFebreroPrev('R');
					else
						ingresos.get(j).setFebreroPrev('P');
				}
				else
					if(mes.equals("03")){
						ingresos.get(j).setMarzo(ingresos.get(j).getMarzo()+cantidad);
						if(i.getPrevision().equals("Real"))
							ingresos.get(j).setMarzoPrev('R');
						else
							ingresos.get(j).setMarzoPrev('P');
					}
					else
						if(mes.equals("04")){
							ingresos.get(j).setAbril(ingresos.get(j).getAbril()+cantidad);
							if(i.getPrevision().equals("Real"))
								ingresos.get(j).setAbrilPrev('R');
							else
								ingresos.get(j).setAbrilPrev('P');
						}
						else
							if(mes.equals("05")){
								ingresos.get(j).setMayo(ingresos.get(j).getMayo()+cantidad);
								if(i.getPrevision().equals("Real"))
									ingresos.get(j).setMayoPrev('R');
								else
									ingresos.get(j).setMayoPrev('P');
							}
							else
								if(mes.equals("06")){
									ingresos.get(j).setJunio(ingresos.get(j).getJunio()+cantidad);
									if(i.getPrevision().equals("Real"))
										ingresos.get(j).setJunioPrev('R');
									else
										ingresos.get(j).setJunioPrev('P');
								}
								else
									if(mes.equals("07")){
										ingresos.get(j).setJulio(ingresos.get(j).getJulio()+cantidad);
										if(i.getPrevision().equals("Real"))
											ingresos.get(j).setJulioPrev('R');
										else
											ingresos.get(j).setJulioPrev('P');
									}
									else
										if(mes.equals("08")){
											ingresos.get(j).setAgosto(ingresos.get(j).getAgosto()+cantidad);
											if(i.getPrevision().equals("Real"))
												ingresos.get(j).setAgostoPrev('R');
											else
												ingresos.get(j).setAgostoPrev('P');
										}
										else
											if(mes.equals("09")){
												ingresos.get(j).setSeptiembre(ingresos.get(j).getSeptiembre()+cantidad);
												if(i.getPrevision().equals("Real"))
													ingresos.get(j).setSeptiembrePrev('R');
												else
													ingresos.get(j).setSeptiembrePrev('P');
											}
											else
												if(mes.equals("10")){
													ingresos.get(j).setOctubre(ingresos.get(j).getOctubre()+cantidad);
													if(i.getPrevision().equals("Real"))
														ingresos.get(j).setOctubrePrev('R');
													else
														ingresos.get(j).setOctubrePrev('P');
												}
												else
													if(mes.equals("11")){
														ingresos.get(j).setNoviembre(ingresos.get(j).getNoviembre()+cantidad);
														if(i.getPrevision().equals("Real"))
															ingresos.get(j).setNoviembrePrev('R');
														else
															ingresos.get(j).setNoviembrePrev('P');
													}
													else
														if(mes.equals("12")){
															ingresos.get(j).setDiciembre(ingresos.get(j).getDiciembre()+cantidad);
															if(i.getPrevision().equals("Real"))
																ingresos.get(j).setDiciembrePrev('R');
															else
																ingresos.get(j).setDiciembrePrev('P');
														}
			ingresos.get(j).setTotal(ingresos.get(j).getTotal()+cantidad);
		}
	}
	
	public String getNombreObra() {
		return nombreObra;
	}
	public void setNombreObra(String nombreObra) {
		this.nombreObra = nombreObra;
	}
	public List<String> getNombreObras() {
		nombreObras = new ArrayList<String>();
		List<Obra> obras = new ArrayList<Obra>();
		obras = obraService.getObras();
		for(Obra o : obras)
			nombreObras.add(o.getNombreObra());
		return nombreObras;
	}
	public void setNombreObras(List<String> nombreObras) {
		this.nombreObras = nombreObras;
	}
	public String getTipoInfo() {
		return tipoInfo;
	}
	public void setTipoInfo(String tipoInfo) {
		this.tipoInfo = tipoInfo;
	}
	/**
	 * @return the tipoInfos
	 */
	public List<String> getTipoInfos() {
		tipoInfos = new ArrayList<String>();
		tipoInfos.add("Base imponible");
		tipoInfos.add("Total");
		return tipoInfos;
	}
	/**
	 * @param tipoInfos the tipoInfos to set
	 */
	public void setTipoInfos(List<String> tipoInfos) {
		this.tipoInfos = tipoInfos;
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


	/**
	 * @return the ingresos
	 */
	public List<Tesoreria> getIngresos() {
		return ingresos;
	}


	/**
	 * @param ingresos the ingresos to set
	 */
	public void setIngresos(List<Tesoreria> ingresos) {
		this.ingresos = ingresos;
	}


	/**
	 * @return the tesoreriaIngresoService
	 */
	public ITesoreriaIngresoService getTesoreriaIngresoService() {
		return tesoreriaIngresoService;
	}


	/**
	 * @param tesoreriaIngresoService the tesoreriaIngresoService to set
	 */
	public void setTesoreriaIngresoService(ITesoreriaIngresoService tesoreriaIngresoService) {
		this.tesoreriaIngresoService = tesoreriaIngresoService;
	}


	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}


	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	public String getTipoFecha() {
		return tipoFecha;
	}

	public void setTipoFecha(String tipoFecha) {
		this.tipoFecha = tipoFecha;
	}

	public List<String> getTipoFechas() {
		tipoFechas = new ArrayList<String>();
		tipoFechas.add("Fecha factura");
		tipoFechas.add("Fecha vencimiento");
		tipoFechas.add("Fecha cobro");
		return tipoFechas;
	}

	public void setTipoFechas(List<String> tipoFechas) {
		this.tipoFechas = tipoFechas;
	}

	/**
	 * @return the tesoreriaGastoService
	 */
	public ITesoreriaGastoService getTesoreriaGastoService() {
		return tesoreriaGastoService;
	}

	/**
	 * @param tesoreriaGastoService the tesoreriaGastoService to set
	 */
	public void setTesoreriaGastoService(ITesoreriaGastoService tesoreriaGastoService) {
		this.tesoreriaGastoService = tesoreriaGastoService;
	}

	/**
	 * @return the gastos
	 */
	public List<Tesoreria> getGastos() {
		return gastos;
	}

	/**
	 * @param gastos the gastos to set
	 */
	public void setGastos(List<Tesoreria> gastos) {
		this.gastos = gastos;
	}

	/**
	 * @return the gastosDesplegados
	 */
	public List<Tesoreria> getGastosDesplegados() {
		return gastosDesplegados;
	}

	/**
	 * @param gastosDesplegados the gastosDesplegados to set
	 */
	public void setGastosDesplegados(List<Tesoreria> gastosDesplegados) {
		this.gastosDesplegados = gastosDesplegados;
	}

	/**
	 * @return the gastoDesplegable
	 */
	public Tesoreria getGastoDesplegable() {
		return gastoDesplegable;
	}

	/**
	 * @param gastoDesplegable the gastoDesplegable to set
	 */
	public void setGastoDesplegable(Tesoreria gastoDesplegable) {
		this.gastoDesplegable = gastoDesplegable;
	}

	public String getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

	public List<String> getTipoFacturas() {
		tipoFacturas = new ArrayList<String>();
		tipoFacturas.add("Real");
		tipoFacturas.add("Previsión");
		return tipoFacturas;
	}

	public void setTipoFacturas(List<String> tipoFacturas) {
		this.tipoFacturas = tipoFacturas;
	}

	/**
	 * @return the tesoreriaSeleccionada
	 */
	public Tesoreria getTesoreriaSeleccionada() {
		return tesoreriaSeleccionada;
	}

	/**
	 * @param tesoreriaSeleccionada the tesoreriaSeleccionada to set
	 */
	public void setTesoreriaSeleccionada(Tesoreria tesoreriaSeleccionada) {
		this.tesoreriaSeleccionada = tesoreriaSeleccionada;
	}

	public void showDesplegable2(SelectEvent event){
		tesoreriaSeleccionada = (Tesoreria) event.getObject();
		List<Gasto> todosGastos = new ArrayList<Gasto>();
		gastosDesplegables = new ArrayList<Gasto>();
		todosGastos = tesoreriaGastoService.getGastos1();
		for(Gasto g : todosGastos){
			if(g.getCat().equals(tesoreriaSeleccionada.getTitulos()) && g.getObra().getNombreObra().equals(nombreObra))
				gastosDesplegables.add(g);
		}
	}

	/**
	 * @return the gastosDesplegables
	 */
	public List<Gasto> getGastosDesplegables() {
		return gastosDesplegables;
	}

	/**
	 * @param gastosDesplegables the gastosDesplegables to set
	 */
	public void setGastosDesplegables(List<Gasto> gastosDesplegables) {
		this.gastosDesplegables = gastosDesplegables;
	}
}
