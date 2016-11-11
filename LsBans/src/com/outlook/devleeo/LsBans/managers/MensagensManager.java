package com.outlook.devleeo.LsBans.managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;


public class MensagensManager {
	
	public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("LsBans").getConfig();
	public static final String PREFIX = getMensagem("Prefix"); 
	
	public static String getMensagem(String path) {
		return formatMessage(config.getString("Mensagens." + path));
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
				.replace("preco", "pre�o")
				.replace("Preco", "Pre�o")
				.replace("invalida", "inv�lida")
				.replace("invalido", "inv�lido")
				.replace("Parabens", "Parab�ns")
				.replace("parabens", "parab�ns")
				.replace("proxima", "pr�xima")
				.replace("Proxima", "Pr�xima")	
				.replace("%>>%", "�")
				.replace("\\n", "\n");
	}

}
