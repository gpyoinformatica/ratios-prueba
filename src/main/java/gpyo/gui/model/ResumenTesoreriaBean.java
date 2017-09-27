package gpyo.gui.model;

import gpyo.persistence.entity.admin.Gasto;
import gpyo.persistence.entity.admin.Ingreso;
import gpyo.persistence.entity.admin.Obra;
import gpyo.persistence.entity.admin.ObraDeUsuario;
import gpyo.persistence.entity.admin.Tesoreria;
import gpyo.persistence.entity.admin.TesoreriaDesplegable;
import gpyo.service.businesslogic.IObraService;
import gpyo.service.businesslogic.ITesoreriaGastoService;
import gpyo.service.businesslogic.ITesoreriaIngresoService;

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
public class ResumenTesoreriaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8078399656765007558L;

	private List<Obra> obras = new ArrayList<Obra>();
	private String year;
	private List<Tesoreria> ingresosBurgos = new ArrayList<Tesoreria>();
	private List<Tesoreria> ingresosBarcelona = new ArrayList<Tesoreria>();
	private List<Tesoreria> ingresosCentral = new ArrayList<Tesoreria>();
	private List<TesoreriaDesplegable> gastosBurgos = new ArrayList<TesoreriaDesplegable>();
	private List<TesoreriaDesplegable> gastosBarcelona = new ArrayList<TesoreriaDesplegable>();
	private List<TesoreriaDesplegable> gastosCentral = new ArrayList<TesoreriaDesplegable>();
	private String tipoInfo;
	private List<String> tipoInfos = new ArrayList<String>();
	private String tipoFecha;
	private List<String> tipoFechas = new ArrayList<String>();
	private String tipoFactura;
	private List<String> tipoFacturas = new ArrayList<String>();
	@Autowired
	private ITesoreriaIngresoService tesoreriaIngresoService;
	@Autowired
	private ITesoreriaGastoService tesoreriaGastoService;
	@Autowired
	private IObraService obraService;

	public void verResumenTesoreria() {
		ingresosBurgos = new ArrayList<Tesoreria>();
		ingresosBarcelona = new ArrayList<Tesoreria>();
		ingresosCentral = new ArrayList<Tesoreria>();
		gastosBurgos = new ArrayList<TesoreriaDesplegable>();
		gastosBarcelona = new ArrayList<TesoreriaDesplegable>();
		gastosCentral = new ArrayList<TesoreriaDesplegable>();
		obras = obraService.getObras();
		for (int x = 0; x < obras.size(); x++) {
			List<Ingreso> ing = new ArrayList<Ingreso>();
			ing = tesoreriaIngresoService.getIngresosPorObra(obras.get(x).getIdObra());
			int j = 0;
			for (Ingreso i : ing) {
				Tesoreria ingTesoreria = new Tesoreria();
				ingTesoreria = calcularIngresos(i, j, tipoFecha, obras.get(x));
				if (ingTesoreria.getTotal() != 0)
					if (obras.get(x).getTitulo().equals("Obras Burgos")) {
						ingresosBurgos.add(ingTesoreria);
					} else if (obras.get(x).getTitulo()
							.equals("Obras Barcelona"))
						ingresosBarcelona.add(ingTesoreria);
					else
						ingresosCentral.add(ingTesoreria);
				j++;
			}
		}
		ingresosBurgos = borrarDuplicados(ingresosBurgos);
		ingresosBarcelona = borrarDuplicados(ingresosBarcelona);
		ingresosCentral = borrarDuplicados(ingresosCentral);
		List<Gasto> gast = new ArrayList<Gasto>();
		int j = 0;
		for (int n = 0; n < obras.size(); n++) {
			gast = tesoreriaGastoService.getGastosPorObra(obras.get(n).getIdObra());
			j = 0;
			for (int i = 0; i < gast.size(); i++)
				for (int x = gast.size() - 1; x > 0; x--)
					if (gast.get(i).getFactura()
							.equals(gast.get(x).getFactura())
							&& i != x)
						gast.remove(x);
			Gasto gastoPersonal = new Gasto();
			gastoPersonal = crearGastoPersonal(obras.get(n).getNombreObra());
			gastoPersonal.setObra(obras.get(n));
			gastoPersonal
					.setCantidad((float) (gastoPersonal.getBaseImponible() + (gastoPersonal
							.getBaseImponible() * 0.21)));
			gastoPersonal.setVencimiento(gastoPersonal.getFecha());
			if (obras.get(n).getTitulo().equals("Obras Barcelona")
					&& gastoPersonal.getCantidad() != 0)
				calcularGastosBarcelona(gastoPersonal, j, tipoFecha);
			else if (obras.get(n).getTitulo().equals("Obras Burgos")
					&& gastoPersonal.getCantidad() != 0)
				calcularGastosBurgos(gastoPersonal, j, tipoFecha);
			else if (gastoPersonal.getCantidad() != 0)
				calcularGastosCentral(gastoPersonal, j, tipoFecha);
			for (Gasto g : gast) {
				if (g.getCentroCoste().equals("Barcelona"))
					calcularGastosBarcelona(g, j, tipoFecha);
				else if (g.getCentroCoste().equals("Burgos"))
					calcularGastosBurgos(g, j, tipoFecha);
				else if (g.getCentroCoste().equals("Central"))
					calcularGastosCentral(g, j, tipoFecha);
				j++;
			}
			gastosBarcelona = borrarGastosDuplicados(gastosBarcelona);
			gastosBurgos = borrarGastosDuplicados(gastosBurgos);
			gastosCentral = borrarGastosDuplicados(gastosCentral);
		}
	}

	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Se tendrá que cambiar el número
	 * por el que se multiplican las horas en función del usuario.
	 * 
	 * No se utiliza, se meterán los costes de personal de manera manual.
	 * 
	 * @param nombreObra
	 * @return
	 */
	public Gasto crearGastoPersonal(String nombreObra) {
		Gasto gastoPersonal = new Gasto();
		List<ObraDeUsuario> ratios = new ArrayList<ObraDeUsuario>();
		ratios = obraService.getObrasPorNombre(nombreObra);
		gastoPersonal.setCat("PERS");
		for (int i = 0; i < ratios.size(); i++) {
			if (year.equals(new StringBuilder()
					.append(ratios.get(i).getFecha().charAt(6))
					.append(ratios.get(i).getFecha().charAt(7))
					.append(ratios.get(i).getFecha().charAt(8))
					.append(ratios.get(i).getFecha().charAt(9)).toString())) {
				gastoPersonal.setBaseImponible(gastoPersonal.getBaseImponible()
						+ (ratios.get(i).getHorasTecnico() * 12)
						+ (ratios.get(i).getHorasAdmin() * 12));
				gastoPersonal.setFecha(ratios.get(i).getFecha());
				gastoPersonal.setFechaPago(gastoPersonal.getFecha());
			}
		}
		return gastoPersonal;
	}

	public List<TesoreriaDesplegable> borrarGastosDuplicados(
			List<TesoreriaDesplegable> gastos) {
		for (int i = 0; i < gastos.size(); i++) {
			for (int x = gastos.size() - 1; x > 0; x--) {
				if (gastos.get(i).getTitulos()
						.equals(gastos.get(x).getTitulos())
						&& i != x) {
					gastos.get(i)
							.setEnero(
									gastos.get(i).getEnero()
											+ gastos.get(x).getEnero());
					gastos.get(i).setFebrero(
							gastos.get(i).getFebrero()
									+ gastos.get(x).getFebrero());
					gastos.get(i)
							.setMarzo(
									gastos.get(i).getMarzo()
											+ gastos.get(x).getMarzo());
					gastos.get(i)
							.setAbril(
									gastos.get(i).getAbril()
											+ gastos.get(x).getAbril());
					gastos.get(i).setMayo(
							gastos.get(i).getMayo() + gastos.get(x).getMayo());
					gastos.get(i)
							.setJunio(
									gastos.get(i).getJunio()
											+ gastos.get(x).getJunio());
					gastos.get(i)
							.setJulio(
									gastos.get(i).getJulio()
											+ gastos.get(x).getJulio());
					gastos.get(i).setAgosto(
							gastos.get(i).getAgosto()
									+ gastos.get(x).getAgosto());
					gastos.get(i).setSeptiembre(
							gastos.get(i).getSeptiembre()
									+ gastos.get(x).getSeptiembre());
					gastos.get(i).setOctubre(
							gastos.get(i).getOctubre()
									+ gastos.get(x).getOctubre());
					gastos.get(i).setNoviembre(
							gastos.get(i).getNoviembre()
									+ gastos.get(x).getNoviembre());
					gastos.get(i).setDiciembre(
							gastos.get(i).getDiciembre()
									+ gastos.get(x).getDiciembre());
					gastos.get(i)
							.setTotal(
									gastos.get(i).getTotal()
											+ gastos.get(x).getTotal());
					for (int y = 0; y < gastos.get(x)
							.getTesoreriasDesplegables().size(); y++)
						gastos.get(i)
								.getTesoreriasDesplegables()
								.add(gastos.get(x).getTesoreriasDesplegables()
										.get(y));
					gastos.remove(x);
				}
			}
		}
		for (int i = 0; i < gastos.size(); i++) {
			for (int j = 0; j < gastos.get(i).getTesoreriasDesplegables()
					.size(); j++) {
				for (int k = gastos.get(i).getTesoreriasDesplegables().size() - 1; k > 0; k--) {
					if (j != k
							&& gastos
									.get(i)
									.getTesoreriasDesplegables()
									.get(j)
									.getTitulos()
									.equals(gastos.get(i)
											.getTesoreriasDesplegables().get(k)
											.getTitulos())) {
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setEnero(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getEnero()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getEnero());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setFebrero(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getFebrero()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getFebrero());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setMarzo(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getMarzo()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getMarzo());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setAbril(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getAbril()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getAbril());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setMayo(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getMayo()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getMayo());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setJunio(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getJunio()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getJunio());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setJulio(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getJulio()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getJulio());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setAgosto(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getAgosto()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getAgosto());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setSeptiembre(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getSeptiembre()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getSeptiembre());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setOctubre(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getOctubre()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getOctubre());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setNoviembre(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getNoviembre()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getNoviembre());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setDiciembre(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getDiciembre()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getDiciembre());
						gastos.get(i)
								.getTesoreriasDesplegables()
								.get(j)
								.setTotal(
										gastos.get(i)
												.getTesoreriasDesplegables()
												.get(j).getTotal()
												+ gastos.get(i)
														.getTesoreriasDesplegables()
														.get(k).getTotal());
						gastos.get(i).getTesoreriasDesplegables().remove(k);
					}
				}
			}
		}
		return gastos;
	}

	public void calcularGastosBarcelona(Gasto g, int j, String opcion) {
		TesoreriaDesplegable gastosPers = new TesoreriaDesplegable();
		gastosPers = calcularGastosMes(g, gastosPers, opcion);
		if (gastosPers.getTotal() != 0)
			gastosBarcelona.add(gastosPers);
	}

	public void calcularGastosBurgos(Gasto g, int j, String opcion) {
		TesoreriaDesplegable gastosPers = new TesoreriaDesplegable();
		/**
		 * Con solo una llamada vale, no hace falta una llamada por categoria.
		 */
		/*
		 * Tesoreria gastosGtos = new Tesoreria(); Tesoreria gastosSum = new
		 * Tesoreria(); Tesoreria gastosVeh = new Tesoreria(); Tesoreria
		 * gastosTfno = new Tesoreria(); Tesoreria gastosMens = new Tesoreria();
		 * Tesoreria gastosServProf = new Tesoreria(); Tesoreria gastosMatOf =
		 * new Tesoreria(); Tesoreria gastosMob = new Tesoreria(); Tesoreria
		 * gastosInfor = new Tesoreria(); Tesoreria gastosSeg = new Tesoreria();
		 * Tesoreria gastosAses = new Tesoreria(); Tesoreria gastosUbu = new
		 * Tesoreria(); Tesoreria gastosBcos = new Tesoreria(); Tesoreria
		 * gastosVarios = new Tesoreria(); Tesoreria gastosAlq = new
		 * Tesoreria(); Tesoreria gastosPtamo = new Tesoreria(); Tesoreria
		 * gastosLibr = new Tesoreria(); Tesoreria gastosAcal = new Tesoreria();
		 * Tesoreria gastosRepr = new Tesoreria(); Tesoreria gastosForm = new
		 * Tesoreria(); Tesoreria gastosLimp = new Tesoreria(); Tesoreria
		 * gastosResumen = new Tesoreria(); Tesoreria gastosGlobal = new
		 * Tesoreria(); Tesoreria gastosFacturacion = new Tesoreria(); Tesoreria
		 * gastosGtosPeru = new Tesoreria(); if(g.getCat().equals("PERS")){
		 */
		gastosPers = calcularGastosMes(g, gastosPers, opcion);
		if (gastosPers.getTotal() != 0)
			gastosBurgos.add(gastosPers);
		/*
		 * } else if(g.getCat().equals("GTOS")){ gastosGtos =
		 * calcularGastosMes(g, gastosGtos, opcion); gastos.add(gastosGtos); }
		 * else if(g.getCat().equals("SUM")){ gastosSum = calcularGastosMes(g,
		 * gastosSum, opcion); gastos.add(gastosSum); } else
		 * if(g.getCat().equals("VEH")){ gastosVeh = calcularGastosMes(g,
		 * gastosVeh, opcion); gastos.add(gastosVeh); } else
		 * if(g.getCat().equals("TFNO")){ gastosTfno = calcularGastosMes(g,
		 * gastosTfno, opcion); gastos.add(gastosTfno); } else
		 * if(g.getCat().equals("MENS")){ gastosMens = calcularGastosMes(g,
		 * gastosMens, opcion); gastos.add(gastosMens); } else
		 * if(g.getCat().equals("SERV. PROF")){ gastosServProf =
		 * calcularGastosMes(g, gastosServProf, opcion);
		 * gastos.add(gastosServProf); } else if(g.getCat().equals("MAT OF")){
		 * gastosMatOf = calcularGastosMes(g, gastosMatOf, opcion);
		 * gastos.add(gastosMatOf); } else if(g.getCat().equals("MOB")){
		 * gastosMob = calcularGastosMes(g, gastosMob, opcion);
		 * gastos.add(gastosMob); } else if(g.getCat().equals("INFOR")){
		 * gastosInfor = calcularGastosMes(g, gastosInfor, opcion);
		 * gastos.add(gastosInfor); } else if(g.getCat().equals("SEG")){
		 * gastosSeg = calcularGastosMes(g, gastosSeg, opcion);
		 * gastos.add(gastosSeg); } else if(g.getCat().equals("ASES")){
		 * gastosAses = calcularGastosMes(g, gastosAses, opcion);
		 * gastos.add(gastosAses); } else if(g.getCat().equals("UBU")){
		 * gastosUbu = calcularGastosMes(g, gastosUbu, opcion);
		 * gastos.add(gastosUbu); } else if(g.getCat().equals("BCOS")){
		 * gastosBcos = calcularGastosMes(g, gastosBcos, opcion);
		 * gastos.add(gastosBcos); } else if(g.getCat().equals("VARIOS")){
		 * gastosVarios = calcularGastosMes(g, gastosVarios, opcion);
		 * gastos.add(gastosVarios); } else if(g.getCat().equals("ALQ")){
		 * gastosAlq = calcularGastosMes(g, gastosAlq, opcion);
		 * gastos.add(gastosAlq); } else if(g.getCat().equals("PTAMO")){
		 * gastosPtamo = calcularGastosMes(g, gastosPtamo, opcion);
		 * gastos.add(gastosPtamo); } else if(g.getCat().equals("LIBR")){
		 * gastosLibr = calcularGastosMes(g, gastosLibr, opcion);
		 * gastos.add(gastosLibr); } else if(g.getCat().equals("ACAL")){
		 * gastosAcal = calcularGastosMes(g, gastosAcal, opcion);
		 * gastos.add(gastosAcal); } else if(g.getCat().equals("REPR")){
		 * gastosRepr = calcularGastosMes(g, gastosRepr, opcion);
		 * gastos.add(gastosRepr); } else if(g.getCat().equals("FORM")){
		 * gastosForm = calcularGastosMes(g, gastosForm, opcion);
		 * gastos.add(gastosForm); } else if(g.getCat().equals("LIMP")){
		 * gastosLimp = calcularGastosMes(g, gastosLimp, opcion);
		 * gastos.add(gastosLimp); } else if(g.getCat().equals("RESUMEN")){
		 * gastosResumen = calcularGastosMes(g, gastosResumen, opcion);
		 * gastos.add(gastosResumen); } else if(g.getCat().equals("GLOBAL")){
		 * gastosGlobal = calcularGastosMes(g, gastosGlobal, opcion);
		 * gastos.add(gastosGlobal); } else
		 * if(g.getCat().equals("FACTURACION")){ gastosFacturacion =
		 * calcularGastosMes(g, gastosFacturacion, opcion);
		 * gastos.add(gastosFacturacion); } else
		 * if(g.getCat().equals("GTOS PERU")){ gastosGtosPeru =
		 * calcularGastosMes(g, gastosGtosPeru, opcion);
		 * gastos.add(gastosGtosPeru); }
		 */
	}

	public void calcularGastosCentral(Gasto g, int j, String opcion) {
		TesoreriaDesplegable gastosPers = new TesoreriaDesplegable();
		gastosPers = calcularGastosMes(g, gastosPers, opcion);
		if (gastosPers.getTotal() != 0)
			gastosCentral.add(gastosPers);
	}

	public TesoreriaDesplegable calcularGastosMes(Gasto g,
			TesoreriaDesplegable tes, String opcion) {
		String mes = " ";
		String yearGasto = " ";
		if (opcion.equals("Fecha factura")) {
			mes = new StringBuilder().append(g.getFecha().charAt(3))
					.append(g.getFecha().charAt(4)).toString();
			yearGasto = new StringBuilder().append(g.getFecha().charAt(6))
					.append(g.getFecha().charAt(7))
					.append(g.getFecha().charAt(8))
					.append(g.getFecha().charAt(9)).toString();
		} else {
			if (opcion.equals("Fecha vencimiento")) {
				mes = new StringBuilder().append(g.getVencimiento().charAt(3))
						.append(g.getVencimiento().charAt(4)).toString();
				yearGasto = new StringBuilder()
						.append(g.getVencimiento().charAt(6))
						.append(g.getVencimiento().charAt(7))
						.append(g.getVencimiento().charAt(8))
						.append(g.getVencimiento().charAt(9)).toString();
			} else if (g.getFechaPago() != null) {
				mes = new StringBuilder().append(g.getFechaPago().charAt(3))
						.append(g.getFechaPago().charAt(4)).toString();
				yearGasto = new StringBuilder()
						.append(g.getFechaPago().charAt(6))
						.append(g.getFechaPago().charAt(7))
						.append(g.getFechaPago().charAt(8))
						.append(g.getFechaPago().charAt(9)).toString();
			}
		}
		tes.setTitulos(g.getCat());
		Tesoreria tesDesp = new Tesoreria();
		tesDesp.setTitulos(g.getObra().getNombreObra());
		float cantidad = 0;
		if (tipoInfo.equals("Base imponible"))
			cantidad = g.getBaseImponible();
		else
			cantidad = g.getCantidad();
		if (g.getPrevision() == null)
			g.setPrevision("Real");
		if (yearGasto.equals(year) && g.getPrevision().equals(tipoFactura)) {
			if (mes.equals("01")) {
				tes.setEnero(tes.getEnero() + cantidad);
				tesDesp.setEnero(cantidad);
			} else if (mes.equals("02")) {
				tes.setFebrero(tes.getFebrero() + cantidad);
				tesDesp.setFebrero(cantidad);
			} else if (mes.equals("03")) {
				tes.setMarzo(tes.getMarzo() + cantidad);
				tesDesp.setMarzo(cantidad);
			} else if (mes.equals("04")) {
				tes.setAbril(tes.getAbril() + cantidad);
				tesDesp.setAbril(cantidad);
			} else if (mes.equals("05")) {
				tes.setMayo(tes.getMayo() + cantidad);
				tesDesp.setMayo(cantidad);
			} else if (mes.equals("06")) {
				tes.setJunio(tes.getJunio() + cantidad);
				tesDesp.setJunio(cantidad);
			} else if (mes.equals("07")) {
				tes.setJulio(tes.getJulio() + cantidad);
				tesDesp.setJulio(cantidad);
			} else if (mes.equals("08")) {
				tes.setAgosto(tes.getAgosto() + cantidad);
				tesDesp.setAgosto(cantidad);
			} else if (mes.equals("09")) {
				tes.setSeptiembre(tes.getSeptiembre() + cantidad);
				tesDesp.setSeptiembre(cantidad);
			} else if (mes.equals("10")) {
				tes.setOctubre(tes.getOctubre() + cantidad);
				tesDesp.setOctubre(cantidad);
			} else if (mes.equals("11")) {
				tes.setNoviembre(tes.getNoviembre() + cantidad);
				tesDesp.setNoviembre(cantidad);
			} else if (mes.equals("12")) {
				tes.setDiciembre(tes.getDiciembre() + cantidad);
				tesDesp.setDiciembre(cantidad);
			}
			tes.setTotal(tes.getTotal() + cantidad);
			tesDesp.setTotal(cantidad);
		}
		tes.getTesoreriasDesplegables().add(tesDesp);
		return tes;
	}

	public List<Tesoreria> borrarDuplicados(List<Tesoreria> listaConDuplicados) {
		for (int i = 0; i < listaConDuplicados.size(); i++) {
			for (int j = listaConDuplicados.size() - 1; j > 0; j--) {
				if (listaConDuplicados.get(i).getTitulos()
						.equals(listaConDuplicados.get(j).getTitulos())
						&& i != j) {
					listaConDuplicados.get(i).setEnero(
							listaConDuplicados.get(i).getEnero()
									+ listaConDuplicados.get(j).getEnero());
					listaConDuplicados.get(i).setFebrero(
							listaConDuplicados.get(i).getFebrero()
									+ listaConDuplicados.get(j).getFebrero());
					listaConDuplicados.get(i).setMarzo(
							listaConDuplicados.get(i).getMarzo()
									+ listaConDuplicados.get(j).getMarzo());
					listaConDuplicados.get(i).setAbril(
							listaConDuplicados.get(i).getAbril()
									+ listaConDuplicados.get(j).getAbril());
					listaConDuplicados.get(i).setMayo(
							listaConDuplicados.get(i).getMayo()
									+ listaConDuplicados.get(j).getMayo());
					listaConDuplicados.get(i).setJunio(
							listaConDuplicados.get(i).getJunio()
									+ listaConDuplicados.get(j).getJunio());
					listaConDuplicados.get(i).setJulio(
							listaConDuplicados.get(i).getJulio()
									+ listaConDuplicados.get(j).getJulio());
					listaConDuplicados.get(i).setAgosto(
							listaConDuplicados.get(i).getAgosto()
									+ listaConDuplicados.get(j).getAgosto());
					listaConDuplicados.get(i)
							.setSeptiembre(
									listaConDuplicados.get(i).getSeptiembre()
											+ listaConDuplicados.get(j)
													.getSeptiembre());
					listaConDuplicados.get(i).setOctubre(
							listaConDuplicados.get(i).getOctubre()
									+ listaConDuplicados.get(j).getOctubre());
					listaConDuplicados.get(i).setNoviembre(
							listaConDuplicados.get(i).getNoviembre()
									+ listaConDuplicados.get(j).getNoviembre());
					listaConDuplicados.get(i).setDiciembre(
							listaConDuplicados.get(i).getDiciembre()
									+ listaConDuplicados.get(j).getDiciembre());
					listaConDuplicados.get(i).setTotal(
							listaConDuplicados.get(i).getTotal()
									+ listaConDuplicados.get(j).getTotal());
					listaConDuplicados.remove(j);
				}
			}
		}
		return listaConDuplicados;
	}

	public Tesoreria calcularIngresos(Ingreso i, int j, String opcion, Obra obra) {
		Tesoreria ingreso = new Tesoreria();
		String mes = " ";
		String yearIngreso = " ";
		if (opcion.equals("Fecha factura")) {
			mes = new StringBuilder().append(i.getFecha().charAt(3))
					.append(i.getFecha().charAt(4)).toString();
			yearIngreso = new StringBuilder().append(i.getFecha().charAt(6))
					.append(i.getFecha().charAt(7))
					.append(i.getFecha().charAt(8))
					.append(i.getFecha().charAt(9)).toString();
		} else {
			if (opcion.equals("Fecha vencimiento")) {
				mes = new StringBuilder()
						.append(i.getFechaVencimiento().charAt(3))
						.append(i.getFechaVencimiento().charAt(4)).toString();
				yearIngreso = new StringBuilder()
						.append(i.getFechaVencimiento().charAt(6))
						.append(i.getFechaVencimiento().charAt(7))
						.append(i.getFechaVencimiento().charAt(8))
						.append(i.getFechaVencimiento().charAt(9)).toString();
			} else if (i.getFechaCobro() != null) {
				mes = new StringBuilder().append(i.getFechaCobro().charAt(3))
						.append(i.getFechaCobro().charAt(4)).toString();
				yearIngreso = new StringBuilder()
						.append(i.getFechaCobro().charAt(6))
						.append(i.getFechaCobro().charAt(7))
						.append(i.getFechaCobro().charAt(8))
						.append(i.getFechaCobro().charAt(9)).toString();
			}
		}
		ingreso.setTitulos(obra.getNombreObra());
		float cantidad = 0;
		if (tipoInfo.equals("Base imponible"))
			cantidad = i.getBaseImponible();
		else
			cantidad = i.getTotalIngreso();
		if (yearIngreso.equals(year) && i.getPrevision().equals(tipoFacturas)) {
			if (mes.equals("01"))
				ingreso.setEnero(ingreso.getEnero() + cantidad);
			else if (mes.equals("02"))
				ingreso.setFebrero(ingreso.getFebrero() + cantidad);
			else if (mes.equals("03"))
				ingreso.setMarzo(ingreso.getMarzo() + cantidad);
			else if (mes.equals("04"))
				ingreso.setAbril(ingreso.getAbril() + cantidad);
			else if (mes.equals("05"))
				ingreso.setMayo(ingreso.getMayo() + cantidad);
			else if (mes.equals("06"))
				ingreso.setJunio(ingreso.getJunio() + cantidad);
			else if (mes.equals("07"))
				ingreso.setJulio(ingreso.getJulio() + cantidad);
			else if (mes.equals("08"))
				ingreso.setAgosto(ingreso.getAgosto() + cantidad);
			else if (mes.equals("09"))
				ingreso.setSeptiembre(ingreso.getSeptiembre() + cantidad);
			else if (mes.equals("10"))
				ingreso.setOctubre(ingreso.getOctubre() + cantidad);
			else if (mes.equals("11"))
				ingreso.setNoviembre(ingreso.getNoviembre() + cantidad);
			else if (mes.equals("12"))
				ingreso.setDiciembre(ingreso.getDiciembre() + cantidad);
			ingreso.setTotal(ingreso.getTotal() + cantidad);
		}
		return ingreso;
	}

	public List<Obra> getObras() {
		return obras;
	}

	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<Tesoreria> getIngresosBurgos() {
		return ingresosBurgos;
	}

	public void setIngresosBurgos(List<Tesoreria> ingresosBurgos) {
		this.ingresosBurgos = ingresosBurgos;
	}

	public List<Tesoreria> getIngresosBarcelona() {
		return ingresosBarcelona;
	}

	public void setIngresosBarcelona(List<Tesoreria> ingresosBarcelona) {
		this.ingresosBarcelona = ingresosBarcelona;
	}

	public List<Tesoreria> getIngresosCentral() {
		return ingresosCentral;
	}

	public void setIngresosCentral(List<Tesoreria> ingresosCentral) {
		this.ingresosCentral = ingresosCentral;
	}

	public String getTipoInfo() {
		return tipoInfo;
	}

	public void setTipoInfo(String tipoInfo) {
		this.tipoInfo = tipoInfo;
	}

	public List<String> getTipoInfos() {
		tipoInfos = new ArrayList<String>();
		tipoInfos.add("Base imponible");
		tipoInfos.add("Total");
		return tipoInfos;
	}

	public void setTipoInfos(List<String> tipoInfos) {
		this.tipoInfos = tipoInfos;
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
		tipoFechas.add("Fecha pago");
		return tipoFechas;
	}

	public void setTipoFechas(List<String> tipoFechas) {
		this.tipoFechas = tipoFechas;
	}

	/**
	 * @return the tesoreriaIngresoService
	 */
	public ITesoreriaIngresoService getTesoreriaIngresoService() {
		return tesoreriaIngresoService;
	}

	/**
	 * @param tesoreriaIngresoService
	 *            the tesoreriaIngresoService to set
	 */
	public void setTesoreriaIngresoService(
			ITesoreriaIngresoService tesoreriaIngresoService) {
		this.tesoreriaIngresoService = tesoreriaIngresoService;
	}

	/**
	 * @return the tesoreriaGastoService
	 */
	public ITesoreriaGastoService getTesoreriaGastoService() {
		return tesoreriaGastoService;
	}

	/**
	 * @param tesoreriaGastoService
	 *            the tesoreriaGastoService to set
	 */
	public void setTesoreriaGastoService(
			ITesoreriaGastoService tesoreriaGastoService) {
		this.tesoreriaGastoService = tesoreriaGastoService;
	}

	/**
	 * @return the obraService
	 */
	public IObraService getObraService() {
		return obraService;
	}

	/**
	 * @param obraService
	 *            the obraService to set
	 */
	public void setObraService(IObraService obraService) {
		this.obraService = obraService;
	}

	public List<TesoreriaDesplegable> getGastosBurgos() {
		return gastosBurgos;
	}

	public void setGastosBurgos(List<TesoreriaDesplegable> gastosBurgos) {
		this.gastosBurgos = gastosBurgos;
	}

	public List<TesoreriaDesplegable> getGastosBarcelona() {
		return gastosBarcelona;
	}

	public void setGastosBarcelona(List<TesoreriaDesplegable> gastosBarcelona) {
		this.gastosBarcelona = gastosBarcelona;
	}

	public List<TesoreriaDesplegable> getGastosCentral() {
		return gastosCentral;
	}

	public void setGastosCentral(List<TesoreriaDesplegable> gastosCentral) {
		this.gastosCentral = gastosCentral;
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

}
