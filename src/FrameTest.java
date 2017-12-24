import javax.swing.JFrame;

public class FrameTest extends JFrame{

	public FrameTest()
	{
		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonTest bu = new ButtonTest();
		add(bu);
		setVisible(true);
	}
}
