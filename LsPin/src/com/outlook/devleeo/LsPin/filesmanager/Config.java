package com.outlook.devleeo.LsPin.filesmanager;

import org.bukkit.configuration.file.FileConfiguration;

import com.outlook.devleeo.LsPin.LsPin;

public class Config {
	
	private static final FileConfiguration CONFIG = LsPin.getInstance().getConfig();
	public static final boolean USAR_NUMEROS_E_LETRAS = CONFIG.getBoolean("NUMEROS_E_LETRAS");
	public static final boolean MAISCULAS_E_MINUSCULAS = CONFIG.getBoolean("MAIUSCULAS_E_MINUSCULAS");
	public static final boolean ATIVAR_PIN_ATUAL = CONFIG.getBoolean("ATIVAR_PIN_ATUAL");
	public static final boolean SALVAR_QUANDO_DESATIVAR = CONFIG.getBoolean("SALVAR_QUANDO_DESATIVAR");
	public static final boolean ATIVAR_MENSAGEM_JOIN = CONFIG.getBoolean("ATIVAR_MENSAGEM_JOIN");
	public static final int TAMANHO = CONFIG.getInt("TAMANHO");
	public static final int MAX_TENTATIVAS = CONFIG.getInt("MAX_TENTATIVAS");
	public static final int TEMPO_BLOQUEIO = CONFIG.getInt("TEMPO_BLOQUEIO");
	public static final int TEMPO_SALVAR_PINS = CONFIG.getInt("TEMPO_SALVAR_PINS");
	
	public static String getMensagem(String mensagem) {
		return formatMessage(CONFIG.getString(mensagem.toUpperCase()));
	}
	
	public static String getPrefix() {
		return formatMessage(CONFIG.getString("PREFIX"));
	}
	
	public static String formatMessage(String msg) {
		return msg
				.replace("&", "�")
				.replace("voce", "voc�")
				.replace("Voce", "Voc�")
				.replace("nao", "n�o")
				.replace("Nao", "N�o")
				.replace("ninguem", "ningu�m")
				.replace("Ninguem", "Ningu�m")
				.replace("numero", "n�mero")
				.replace("Numero", "N�mero")
				.replace("ja", "j�")
				.replace("sera", "ser�")
				.replace("proxima", "pr�xima")
				.replace("Proxima", "Pr�xima")
				.replace("seguranca", "seguran�a")
				.replace("Seguranca", "Seguran�a")
				.replace("<prefix>", CONFIG.getString("PREFIX").replace("&", "�"))
				.replace("%>>%", "�")
				.replace("\\n", "\n");
	}

}
