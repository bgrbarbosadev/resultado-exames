package br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.exceptions;

public class LaudoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LaudoNotFoundException(String msg) {
		super(msg);
	}
}
