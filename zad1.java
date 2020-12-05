import java.util.Random;

class tablica
{
	private int[][] liczby;
	
	public tablica(int il_wierszy, int il_kolumn)
	{
		liczby = new int[il_wierszy][il_kolumn];
		Random generator = new Random();
		for (int i=0; i<liczby.length; i++)
		{
			for (int j=0; j<liczby[0].length; j++)
			{
				liczby[i][j] = generator.nextInt(10);
			}
		}
	}
	
	public void displayTablica()
	{
		for (int i=0; i<liczby.length; i++)
		{
			for (int j=0; j<liczby[0].length; j++)
			{
				System.out.print(liczby[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public int sumaTablica()
	{
		int suma = 0;
		for (int i=0; i<liczby.length; i++)
		{
			for (int j=0; j<liczby[0].length; j++)
			{
				suma+=liczby[i][j];
			}
		}
		return suma;
	}
	
	public double sredniaTablica()
	{
		return (double)(this.sumaTablica())/(double)(liczby.length*liczby[0].length);
	}
	
	public int maxTablica()
	{
		int max = liczby[0][0];
		for (int i=0; i<liczby.length; i++)
		{
			for (int j=0; j<liczby[0].length; j++)
			{
				if (liczby[i][j]>max) {
					max = liczby[i][j];
				}
			}
		}
		return max;
	}
	
	public int sumaWierszTablica(int index_wiersza)
	{
		int suma = 0;
		for (int j=0; j<liczby[index_wiersza].length; j++)
		{
			suma+=liczby[index_wiersza][j];
		}
		return suma;
	}
	
	public int[] przepiszKolumna(int index_kolumny)
	{
		int[] kolumna = new int[liczby.length];
		int[][] swap_liczby = new int[liczby.length][liczby[0].length-1];
		for (int i=0; i<liczby.length; i++)
		{
			for (int j=0; j<liczby[0].length; j++)
			{
				if (j<index_kolumny) {
					swap_liczby[i][j] = liczby[i][j];
				}
				if (j == index_kolumny) {
					kolumna[i] = liczby[i][j];
				}
				if (j>index_kolumny) {
					swap_liczby[i][j-1] = liczby[i][j];
				}
			}
		}
		liczby = swap_liczby;
		return kolumna;
	}
}

class zad1 
{
	public static void main(String[] args) 
	{
        tablica obj1 = new tablica(5, 4);
        obj1.displayTablica();
        System.out.println(obj1.sumaTablica());
        System.out.println(obj1.sredniaTablica());
        System.out.println(obj1.maxTablica());
        System.out.println(obj1.sumaWierszTablica(1));
        int[] kolumna = obj1.przepiszKolumna(1);
        for (int i=0; i<kolumna.length; i++)
        {
        	System.out.print(kolumna[i] + " ");
        }
        System.out.println();
        obj1.displayTablica();
    }
}
