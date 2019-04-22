import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
	
	public Word root = null;
	public int size = 0;
	Scanner sc = new Scanner(System.in);
	
	public void exe()
	{
		while(true)
		{			
			read_file();			
			while(true)
			{
				System.out.print("$ ");				
				String command = sc.nextLine();
				String[] s = command.split(" ");
				if(s[0].compareTo("add") == 0)
					user_add_func();
				else if(s[0].compareTo("find")==0 && s.length >1)
					user_find_func(s[1]);
				else if(s[0].compareTo("delete")==0 && s.length >1)
				{
					int k = user_delete_func(s[1]);
					if(k ==0)
						System.out.println("Not Found");
					else
						System.out.println("Deleted successfully.");
				}
				else if(s[0].compareTo("deleteall")==0 && s.length >1)
				{
					int k = user_deleteall_func(s[1]);
					if(k > 0)
						System.out.println(k + " words were deleted successfully.");					
				}
				else if(s[0].compareTo("size") == 0)
					System.out.println(size);
				else if(s[0].compareTo("exit")==0)
					break;
				else
					System.out.println("Invaild Command.");
			}
			sc.close();
		}
	}
	
	private int user_deleteall_func(String s) {
		Scanner r;
		int num = 0;
		try {
			r = new Scanner(new File(s));
			while(r.hasNext())
			{
				String line = r.nextLine();
				int k = user_delete_func(line);
				if(k > 0)
					num++;				
			}
			r.close();
			
		} catch (FileNotFoundException e)
		{
			System.out.println("No file exist.");
		}
		return num;		
	}

	private int user_delete_func(String s)
	{				
		ArrayList<Word> list = find(s);		
		if(list.size() == 0)
			return 0;
		return delete(list);		
	}
	
	private Word min(Word w)
	{
		while(w.left != null)
			w = w.left;
		return w;
	}
	
	private Word successor(Word w)
	{		
		if(w == null)
			return null;
		if(w.right !=null)					
			return min(w.right);		
		else
		{
			Word y = w.parent;
			while(y.parent != null && y.parent.left != y)			
				y = y.parent;
			return y.parent;
		}	
	}
	
	private int delete(ArrayList<Word> list)
	{
		
		int count = 0;
		for(int i = 0 ; i < list.size(); i++)
		{
			Word x = null;
			Word y = null;
			Word z = list.get(i);
			if(z.left == null || z.right ==null)
				y = z;
			else
				y = successor(list.get(i));			
			if(y.left != null)
				x = y.left;
			else
				x = y.right;			
			
			if(x != null)
				x.parent = y.parent;			
			
			if(y.parent == null)
				root = x;
			else if( y == y.parent.left)
				y.parent.left = x;
			else
				y.parent.right = x;
			
			if( y != z)
				z.word = y.word;
			size--;
			count++;
			}
		return count;
	}
	
	private void user_find_func(String s) {
		if(s.compareTo("") == 0)
			return;
		ArrayList<Word> list = find(s);
		for(int i = 0; i<list.size() ; i++)
			list.get(i).print();
	}
	
	private ArrayList<Word> find(String s)
	{
		ArrayList<Word> list = new ArrayList<Word>();
		Word w = root;
		while(w != null)
		{
			if(w.word.compareToIgnoreCase(s) == 0)
			{
				list.add(w);
				w = w.left;
			}
			else if(w.word.compareToIgnoreCase(s) <= 0)
				w = w.right;
			else
				w = w.left;
			}
		return list;
	}
	
	private void read_file()
	{
		Scanner r;
		try {
			r = new Scanner(new File("shuffled_dict.txt"));
			while(r.hasNext())
			{
				String line = r.nextLine();
				split_add(line);			
			}
			r.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("No file exist.");
		}		
	}
	
	private void split_add(String line) {
		if(line.compareTo("")==0)
			return;
		String word = "";
		String pos = "";
		String mean = "";
		int i = 0;
		
		for( ; line.charAt(i) != '(' ; i++)
			word+=line.charAt(i);
		word = word.substring(0, word.length()-1);
		i=i+1;
		for( ; line.charAt(i) != ')' ; i++)
			pos+=line.charAt(i);
		
		if(i+2<line.length())
			mean = line.substring(i+2);		
		insert(new Word(word,pos,mean));		
	}
	
	private void insert(Word w)
	{
		Word y = null;
		Word x = root;
		while(x!=null)
		{
			y = x;
			if(w.word.compareToIgnoreCase(x.word) <= 0)
				x = x.left;
			else if(w.word.compareToIgnoreCase(x.word) > 0)
				x = x.right;
		}
		if(y==null)
			root = w;
		else if(w.word.compareToIgnoreCase(y.word) <= 0)
		{
			y.left = w;
			w.parent = y;
		}
		else
		{
			y.right = w;
			w.parent = y;
		}
		
		size++;
	}
	
	private void user_add_func()
	{		
		String word;
		String pos;
		String meaning;
		System.out.print("	word : ");
		word = sc.nextLine();
		System.out.print("	class : ");
		pos = sc.nextLine();
		System.out.print("	meaning : ");
		meaning = sc.nextLine();
		insert(new Word(word,pos,meaning));
		size++;
	}
}
