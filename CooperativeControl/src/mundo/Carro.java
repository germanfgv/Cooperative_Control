package mundo;

import java.util.Observable;


public class Carro extends Observable{

	private final static int CAPACIDAD=9;
	private final static int MOVING_TIME=1000;
	private final static int WAITING_TIME=MOVING_TIME/4;
	private final static int HARVEST_TIME=3000;

	public final static int STOPPED=0;
	public final static int MOVING=1;
	public final static int HARVESTING=2;
	public final static int STORING=3;

	private int codigo;
	private Camino caminoEnSeguimiento;
	private Camino[] posiblesCaminos;
	private double angulo;
	private int carga;
	private boolean cargado;
	private ManejadorCarro manejadorCarro; 
	private Huerto huerto;

	/**
	 * case(state)
	 * 0:stopped
	 * 1:movement
	 * 2:harvesting
	 * 3:leavingProdudcts
	 */
	//	public int state;
	private Nodo nodoActual;



	public Carro(int cod, Nodo initialNode, double angInicial){
		codigo=cod;
		nodoActual=initialNode;
		caminoEnSeguimiento =null;
		posiblesCaminos=new Camino[4];
		angulo=angInicial;
		carga=0;
		huerto=null;
		manejadorCarro=null;

	}

	public void avanzarEnCamino(){

		if(caminoEnSeguimiento!=null)
		{
			Nodo firstNode=caminoEnSeguimiento.darPrimerNodo();
			if(firstNode!=null)
			{
				firstNode.setaUtilizar(false);
				nodoActual=caminoEnSeguimiento.eliminarPrimerNodoSecuencia();
				notifyChange(0.0);

			}else
			{
				caminoEnSeguimiento.eliminarPrimerNodoSecuencia();
				System.out.println("No hay elementos bien dispuestos en la secuencia del camino a seguir");
			}
		}
	}

	public void recolectar() {
			if(huerto.decrementarFrutos()>0){
			carga++;
			huerto.setEstado(Huerto.DISPONIBLE);
			}else{
				huerto.setEstado(Huerto.VACIO);
			}
			System.out.print("Carro");
			System.out.println(codigo);
			System.out.println(carga);

	}

	public int  evaluarRecoleccion() 
	{
		int estado;		
		if(huerto!=null)
		{
			estado=huerto.getEstado();
			if(estado==Huerto.DISPONIBLE){
				huerto.setEstado(Huerto.EN_RECOLECCION);				
			}
			return estado;
		}
		return Huerto.ERROR;
	}

	public boolean evaluarActHuerto(){
		boolean rta=false;

		if(nodoActual.getHuerto()!=null){
			huerto=nodoActual.getHuerto();
			rta=true;
		}else{
		System.out.println("El nodo actual no es un huerto, error en el avance");
		}

		return rta;
	}



	public boolean evaluarSiguienteMovimiento() 
	{
		Nodo n= caminoEnSeguimiento.darPrimerNodo();
		if(n!=null)
		{
			if(!n.isaUtilizar())
			{
				n.setaUtilizar(true);
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	public void iniciarMovimiento(){
		manejadorCarro=new ManejadorCarro(this, MOVING_TIME, HARVEST_TIME,WAITING_TIME);
		manejadorCarro.start();
	}

	public void notifyChange(double angulo)
	{
		this.angulo=angulo;
		setChanged();
		notifyObservers(codigo);

	}	

	///Getters And Setters

	/**
	 * @param i Posición del arreglo en el cual se insertará el Camino c
	 * @param c Camino a insertar en la posición i del arrglo.
	 */
	public void setCaminoI(int i , Camino c){
		posiblesCaminos[i]=c;
	}

	public boolean getCargado()
	{
		return cargado;
	}

	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * @return the camiloEnSeguimiento
	 */
	public Camino getCaminoEnSeguimiento() {
		return caminoEnSeguimiento;
	}

	/**
	 * @param camiloEnSeguimiento the camiloEnSeguimiento to set
	 */
	public void setCaminoEnSeguimiento(Camino caminoEnSeguimiento) {
		this.caminoEnSeguimiento = caminoEnSeguimiento;
	}

	/**
	 * @return the posiblesCaminos
	 */
	public Camino[] getPosiblesCaminos() {
		return posiblesCaminos;
	}

	/**
	 * @param posiblesCaminos the posiblesCaminos to set
	 */
	public void setPosiblesCaminos(Camino[] posiblesCaminos) {
		this.posiblesCaminos = posiblesCaminos;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the posX
	 */
	public int getPosX() {
		return nodoActual.getPosX();
	}


	/**
	 * @return the posY
	 */
	public int getPosY() {
		return nodoActual.getPosY();
	}

	/**
	 * @return the angulo
	 */
	public double getAngulo() {
		return angulo;
	}
	/**
	 * @param angulo the angulo to set
	 */
	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}

	/**
	 * @return the carga
	 */
	public int getCarga() {
		return carga;
	}
	/**
	 * @param carga the carga to set
	 */
	public void setCarga(int carga) {
		if(!cargado)
		{
			this.carga = carga;
			if (carga==CAPACIDAD)
			{
				cargado=true;
			}
		}

	}

	/**
	 * @return the manejadorCarro
	 */
	public ManejadorCarro getManejadorCarro() {
		return manejadorCarro;
	}

	/**
	 * @param manejadorCarro the manejadorCarro to set
	 */
	public void setManejadorCarro(ManejadorCarro manejadorCarro) {
		this.manejadorCarro = manejadorCarro;
	}


	/**
	 * @return the nodoActual
	 */
	public Nodo getNodoActual() {
		return nodoActual;
	}

	/**
	 * @param nodoActual the nodoActual to set
	 */
	public void setNodoActual(Nodo nodoActual) {
		this.nodoActual = nodoActual;
	}

	/**
	 * @param cargado the cargado to set
	 */
	public void setCargado(boolean cargado) {
		this.cargado = cargado;
	}









}
