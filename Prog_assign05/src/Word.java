
public class Word {
	public String word;
	public String pos;
	public String meaning;
	public Word parent;
	public Word left;
	public Word right;
	
	public Word(String word, String pos, String meaning)
	{
		this.word = word;
		this.pos = pos;
		this.meaning = meaning;
	}
	
	public void print()
	{
		System.out.println(" word : " + this.word);
		System.out.println(" class : " + this.pos);
		System.out.println(" meaning : " + this.meaning + "\n");
	}
}
