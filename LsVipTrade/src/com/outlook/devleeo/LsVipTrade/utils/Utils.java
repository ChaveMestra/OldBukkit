package com.outlook.devleeo.LsVipTrade.utils;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.outlook.devleeo.LsVipTrade.Main;
import com.outlook.devleeo.LsVipTrade.comandos.Comandos;
import com.outlook.devleeo.LsVipTrade.hooks.VipSystem;

public class Utils {
	
	public static VipSystem vipSystem;
	public static boolean useMysql;
	public static String url;
	public static String host;
	public static String porta;
	public static String senha;
	public static String user;
	public static String database;
	protected static final int TEMPO_MAX = Main.getInstance().getConfig().getInt("Tempo_Maximo_Venda");
	protected static final int TEMPO_ENTRE_MSGS = Main.getInstance().getConfig().getInt("Tempo_Entre_Mensagens");
	protected static List<String> vendendoMensagens = Main.getInstance().getConfig().getStringList("Mensagens.Vendendo");
	protected static List<String> precoInvalido = Main.getInstance().getConfig().getStringList("Mensagens.Preco_Invalido");
	protected static List<String> vendaFinalizada = Main.getInstance().getConfig().getStringList("Mensagens.Venda_Finalizada");
	protected static Map<Player, String> vipAVenda = new HashMap<>();
	
	public static boolean isKeyValida(String key) {
		if (vipSystem == VipSystem.VIP_ZERO) {
			if (useMysql) {
				try(PreparedStatement ps = Main.con.prepareStatement("SELECT `key` FROM `keys` WHERE `key`='" + key +"'");) {
					ResultSet rs = ps.executeQuery();
					return rs.next();
				} catch (SQLException e) {
					Bukkit.getConsoleSender().sendMessage("�4[LsVipTrade] �cOcorreu um erro ao tentar validar uma key via mysql. Erro:");
					e.printStackTrace();
				}
			} else {
				Plugin vz = Bukkit.getPluginManager().getPlugin("VipZero");
				if (vz.getConfig().getString("keys") == null) {
					return false;
				}
				Set<String> keys = vz.getConfig().getConfigurationSection("keys.").getKeys(false);
				return keys.contains(key);
			}
		} else if (vipSystem == VipSystem.EASY_VIP) {
			if (useMysql) {
				try(PreparedStatement ps = Main.con.prepareStatement("SELECT `code` FROM `codes` WHERE `code`='" + key + "' AND `used`=0");) {
					ResultSet rs = ps.executeQuery();
					return rs.next();
				} catch (SQLException e) {
					Bukkit.getConsoleSender().sendMessage("�4[LsVipTrade] �cOcorreu um erro ao tentar validar uma key via mysql. Erro:");
					e.printStackTrace();
				}
			} else {
				FileConfiguration codes = YamlConfiguration.loadConfiguration(new File(Bukkit.getPluginManager().getPlugin("EasyVIP").getDataFolder() + File.separator + "Codes.yml"));
				Set<String> keys = new HashSet<>();
				for (String a : codes.getConfigurationSection("Codes").getKeys(false)) {
					if (codes.getInt("Codes." + a) == 0) {
						keys.add(a);
					}
				}
				return keys.contains(key);
			}
		}
		return false;
	}
	
	public static boolean isAuthorized(Player sender, String perm) {
		if (sender.hasPermission(perm)) {
			return true;
		} else {
			sender.sendMessage("�cVoc� n�o tem acesso a este comando.");
			return false;
		}
	}
	
	public static void cancelarVenda() {
		Bukkit.getScheduler().cancelTasks(Main.getInstance());
		Bukkit.broadcastMessage(getMensagem("Venda_Cancelada"));
		vipAVenda.clear();
		Comandos.confirmar.clear();
	}
	
	public static void finalizarVenda() {
		vipAVenda.clear();
		Comandos.confirmar.clear();
		Bukkit.getScheduler().cancelTasks(Main.getInstance());
	}
	
	public static int getPreco(String preco) {
		preco = preco.toLowerCase();
		try {
			long preco2 = Long.parseLong(preco.replace("k", "000"));
			long precoMaximo = Main.getInstance().getConfig().getLong("Preco.Max");
			long precoMinimo = Main.getInstance().getConfig().getLong("Preco.Min");
			if (preco2 > precoMaximo || preco2 < precoMinimo) { 
				return -1;
			} else {
				return (int) preco2;
			}
		} catch(NumberFormatException e) {
			return -1;
		}
	}
	
	public static void ativarVip(Player p, String key) {
		switch (vipSystem) {
		case VIP_ZERO:
			p.chat("/usarkey " + key);
			break;
		case EASY_VIP:
			p.chat("/ev claim " + key);
			break;
		default:
			break;
		}
	}
	
	public static void setupMysql(String host, String porta, String user, String senha, String database) {
		useMysql = true;
		Utils.host = host;
		Utils.porta = porta;
		Utils.user = user;
		Utils.senha = senha;
		Utils.database = database;
		
		Utils.url = "jdbc:mysql://" + Utils.host +":" + Utils.porta + "/" + Utils.database;
	}
	
	public static String getMensagem(String path) {
		return formatMessage(Main.getInstance().getConfig().getString("Mensagens." + path));
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
				.replace("%>>%", "�");
	}
	
	
}
