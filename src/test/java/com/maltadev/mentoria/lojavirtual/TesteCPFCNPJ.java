package com.maltadev.mentoria.lojavirtual;

import com.maltadev.mentoria.lojavirtual.util.ValidaCNPJ;
import com.maltadev.mentoria.lojavirtual.util.ValidaCPF;

public class TesteCPFCNPJ {

	public static void main(String[] args) {
		boolean isCnpj = ValidaCNPJ.isCNPJ("90.357.783/0001-58");
		boolean isCpf = ValidaCPF.isCPF("360.053.350-90");
		
		System.out.println("CNPJ: " + isCnpj);
		System.out.println("CPF: " + isCpf);
	}
	
}
