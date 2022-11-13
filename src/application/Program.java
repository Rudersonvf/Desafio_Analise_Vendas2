package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre com o caminho do arquivo: ");
		String path = sc.next();
		System.out.println();
		
		List<Sale> sales = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			String lines = br.readLine();
			while(lines != null) {
				String[] fields = lines.split(",");
				sales.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				lines = br.readLine();
			}
			
			
			Map<String, Double> mapSell = new HashMap<>();
			for(Sale sale : sales) {
				
				double totalPerSeller = sales.stream().filter(p -> p.getSeller().equals(sale.getSeller()))
						.map(p -> p.getTotal()).reduce(0.0, (x,y) -> x + y);
				
				mapSell.put(sale.getSeller(), totalPerSeller);
			}
			
			System.out.println("Total de vendas por vendedor:");
	
			for(String key : mapSell.keySet()) {
				System.out.println(key + " - R$ " + String.format("%.2f", mapSell.get(key)));
			}
			
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
		sc.close();
	}

}
