package com.fabriciojf.standalone.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * API para tratamento de datas
 * 
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 17/10/2012
 * @version 1.0
 */
public class DateTimeHelper {

	private Date data;
	private Calendar calendar = Calendar.getInstance();
	
	/**
	 * Construtor vazio gera a data atual, o mesmo que new DateTime(new Date());</br> 
	 * <code>new DateTime();</code>
	 */
	public DateTimeHelper() {
		this.data = new Date();
		calendar.setTimeInMillis(data.getTime()); 
	}
	
	/**
	 * Construtor passando data no formato {@link String}</br> 
	 * <code>new DateTime("dd/MM/yyyy");</code>
	 */
	public DateTimeHelper(String data) {
		this.data = strToDate(data);
		calendar.setTimeInMillis(this.data.getTime());
	}
	
	/**
	 * Construtor passando data no formato {@link Date}</br> 
	 * <code>new DateTime(new java.util.Date());</code>
	 */
	public DateTimeHelper(Date data) {
		this.data = data;
		calendar.setTimeInMillis(data.getTime());
	}
	
	/**
	 * Construtor passando data no formato {@link XMLGregorianCalendar}</br> 
	 * <code>new DateTime(xmlGregorianCalendar);</code>
	 */
	public DateTimeHelper(XMLGregorianCalendar gcalendar) {
		this.data = gcalendar.toGregorianCalendar().getTime();
		calendar.setTimeInMillis(this.data.getTime());
	}
	
	/**
	 * Formata a data aplicando a mascara passada</br> 
	 * <code>
	 * new DateTime("01/12/2012").formatar("MM-dd-yyyy"); retorna 12-01-2012
	 * </code>
	 */
	public String formatar(String mascara) {
		SimpleDateFormat formato = new SimpleDateFormat(mascara);
		return formato.format(calendar.getTime());
	}

	 /**
     * Retorna a Hora da data contida na classe (formatar("HH:mm"))
     * @return nos formatos 6:25 ou 18:25 
     */
    public String getHoraMin() {
    	return formatar("HH:mm");
    }

    /**
     * Retorna a Hora:minuto:segundo da data contida na classe (formatar("HH:mm:ss"))
     * @return nos formatos 6:25 ou 18:25
     */
    public String getHoraMinSeg() {
        return formatar("HH:mm:ss");
    }
    
    /**
	 * Subtrai dias em um calendar</br>
	 * <code>
	 * new DateTime("06/12/2012").subtrairDias(5); retorna 01/12/2012
	 * </code>
	 */
	public DateTimeHelper subtrairDias(int dias) {		
		calendar.setTimeInMillis(data.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, dias*-1);
		
		return this;
	}
	
	/**
	 * Soma dias a um calendar</br>
	 * <code>
	 * new DateTime("01/12/2012").somarDias(5); retorna 06/12/2012
	 * </code>
	 */
	public DateTimeHelper somarDias(int dias) {		
		calendar.setTimeInMillis(data.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, dias);
		
		return this;
	}

	/**
	 * Retorna se uma data é um feriado nacional</br>
	 * <code>
	 * new DateTime("01/01/2012").isFeriadoNacional(); retorna true
	 * </code>
	 */
	public boolean isFeriadoNacional() {
		
		calendar.setTimeInMillis(data.getTime());
		
		String ano = String.valueOf(calendar.get(Calendar.YEAR));
		String dataFormatada = formatar("dd/MM/yyyy");

		List<String> feriados = new ArrayList<String>();

		// feriados nacionais
		feriados.add("01/01/");
		feriados.add("21/04/");
		feriados.add("01/05/");
		feriados.add("07/09/");
		feriados.add("12/10/");
		feriados.add("02/11/");
		feriados.add("15/11/");
		feriados.add("25/12/");

		// varre a lista de feriados
		for (String dia : feriados) {
			if (dataFormatada.equals(dia + ano)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Soma minutos (min) em uma data</br>
	 * <code>
	 * new DateTime("01/01/2012").somarMinutos(10); 
	 * </code>
	 * adiciona 10 minutos à data contida na classe
	 */
	public DateTimeHelper somarMinutos(int min) {
		calendar.setTimeInMillis(data.getTime());
		calendar.add(Calendar.MINUTE, min);

		return this;
	}
	
	/**
	 * Subtrai minutos (min) em uma data</br>
	 * <code>
	 * new DateTime("01/01/2012").subtrairMinutos(10); 
	 * </code>
	 * subtrai 10 minutos à data contida na classe
	 */
	public DateTimeHelper subtrairMinutos(int min) {
		calendar.setTimeInMillis(data.getTime());
		calendar.add(Calendar.MINUTE, min*-1);

		return this;
	}
	
	/**
	 * Captura a data contida na classe no formato {@link XMLGregorianCalendar}
	 *
	 * @param date
	 * @return
	 */
	public XMLGregorianCalendar parser() {

		DatatypeFactory dataTypeFactory;
		try {
			dataTypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(data.getTime());

		return dataTypeFactory.newXMLGregorianCalendar(gc);
	}
	
	/**
	 * Retorna a data passada na primeira hora do dia</br>
	 * ex: 01/01/2001 00:00:00
	 */
	public Date getPrimeiraHoraDoDia() {

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(data.getTime());
		cal.add(Calendar.HOUR, 0);
		cal.add(Calendar.MINUTE, 0);
		cal.add(Calendar.SECOND, 0);

		return cal.getTime();
	}

	/**
	 * Retorna a data passada na primeira hora do dia</br>
	 * ex: 01/01/2001 23:59:59
	 */
	public Date getUltimaHoraDoDia() {	
		calendar.setTimeInMillis(data.getTime());
		calendar.add(Calendar.HOUR, 23);
		calendar.add(Calendar.MINUTE, 59);
		calendar.add(Calendar.SECOND, 59);

		return calendar.getTime();
	}
	
	/**
	 * Retorna o ultimo dia do mes
	 * @param data
	 * @return
	 */
	public Date getUltimoDiaDoMes() {
		Integer mes = Integer.parseInt(formatar("MM")) + 1;		
		Date proximoMes = new DateTimeHelper(
				"01/" + mes + "/" + formatar("yyyy")).toDate();

        Calendar calendarData = Calendar.getInstance();
        calendarData.setTime(proximoMes);
        calendarData.add(Calendar.DAY_OF_MONTH,-1);
        return calendarData.getTime();
	}

	/**
	 * Retorna o ultimo dia do mes
	 * @param data
	 * @return
	 */
	public Date getPrimeiroDiaDoMes() {
		String mesAno = formatar("MM/yyyy");
		return new DateTimeHelper("01/" + mesAno).toDate();
	}
	
	/**
	 * Retorna o dia corrente
	 * @param data
	 * @return
	 */
	public String getDiaCorrente() {
		return formatar("dd");
	}
	
	/**
	 * Retorna o mes corrente
	 * @param data
	 * @return
	 */
	public String getMesCorrente() {
		return formatar("MM");
	}
	
	/**
	 * Retorna o ano corrente
	 * @param data
	 * @return
	 */
	public String getAnoCorrente() {
		return formatar("yyyy");
	}
	
	/**
	 * Retorna a data no padrao para criacao de arquivos e diretorios ordenados
	 * ex: 20150209
	 * @param data
	 * @return
	 */
	public String getAnoMesDiaCorrente() {
		return formatar("yyyyMMdd");
	}
	
	/**
	 * Retorna a data contida na classe no formato {@link Date}
	 */
	public Date toDate() {
		return calendar.getTime();
	}
	
	/**
	 * Retorna a data contida na classe no formato {@link Date}
	 */
	public String toString() {
		return formatar("dd/MM/yyyy");
	}
	
	/**
	 * Converte um string strToDate("25/01/10") em data no formato dd/MM/yyyy
	 */
	private Date strToDate(String strData) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return formatter.parse(strData);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
