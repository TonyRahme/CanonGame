package CanonGame;
import Physics.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainGame extends JFrame implements ActionListener{
	public int player = 1;
	public static final int player1 = 1;
	public static final int player2 = 2;
	public JPanel gui, playerP, angleP, speedP;
	public JButton fire, anglePlus,angleMinus,speedPlus,speedMinus;
	public JLabel playerLabel, angleLabel, speedLabel;
	public JTextField angleInput, speedInput;
	public JTextArea stats;
	public String statsString = "";
	public boolean gameEnd = false;
	public int gameRound = 1;
	public double c1angle=0, c2angle=0, c1speed=0, c2speed=0;
	public double score1=0, score2=0, turn1=1,turn2=1;
	Timer gameTimer = new Timer(1000/30, new TimerListener());
	
	static Texture game = new Texture(1200, 700);
	public MainGame(String title)
	{
		super(title);
		gui = new JPanel();
		gui.setLayout(new BoxLayout(gui,BoxLayout.Y_AXIS));
		playerP = new JPanel();
		playerP.setLayout(new BorderLayout());
		playerLabel = new JLabel("Player1");
		playerP.add(playerLabel, BorderLayout.CENTER);
		gui.add(playerP);
		angleP = new JPanel();
		angleLabel = new JLabel("Angle: 0");
		anglePlus = new JButton("+");
		angleInput = new JTextField(3);
		angleInput.setText("0");
		angleMinus = new JButton("-");
		angleP.add(angleLabel);angleP.add(anglePlus);angleP.add(angleInput);angleP.add(angleMinus);
		gui.add(angleP);
		speedP = new JPanel();
		speedLabel = new JLabel("Speed: 0");
		speedPlus = new JButton("+");
		speedInput = new JTextField(3);
		speedInput.setText("0");
		speedMinus = new JButton("-");
		speedP.add(speedLabel);speedP.add(speedPlus);speedP.add(speedInput);speedP.add(speedMinus);
		gui.add(speedP);
		stats = new JTextArea(5,5);
		JScrollPane scrollPane = new JScrollPane(stats);
		stats.setEditable(false);
		fire = new JButton("Fire");
		gui.add(fire);gui.add(scrollPane);
		game.add(gui);
		add(game);
		speedPlus.addActionListener(this);
		speedMinus.addActionListener(this);
		anglePlus.addActionListener(this);
		angleMinus.addActionListener(this);
		fire.addActionListener(this);
		game.timer.start();
		gameTimer.start();
		
		
		
		
		
		
		
		
	}
	public static void main(String[] args){
		MainGame frame = new MainGame("Cannon");
		frame.setSize(1200,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == angleMinus)
			switch(player)
			{
			case player1:
				c1angle -= Double.parseDouble(angleInput.getText())%90;
				c1angle = Math.round(c1angle*100)/100.0d;
				c1angle %= 90;
				if(c1angle < 0) c1angle = 90 + c1angle;
				angleLabel.setText("Angle: "+String.valueOf(c1angle));
				game.cannon1.setRotation(c1angle);
				break;
			case player2:
				c2angle -= Double.parseDouble(angleInput.getText())%90;
				c2angle = Math.round(c2angle*100)/100.0d;
				if(c2angle < 0) c2angle = 90 + c2angle;
				c2angle %= 90;
				angleLabel.setText("Angle: "+String.valueOf(c2angle));
				game.cannon2.setRotation(180-c2angle);
			}
		else if(e.getSource() == anglePlus)
			switch(player)
			{
			case player1:
				c1angle += Double.parseDouble(angleInput.getText());
				c1angle = Math.round(c1angle*100)/100.0d;
				c1angle %= 90;
				angleLabel.setText("Angle: "+String.valueOf(c1angle));
				game.cannon1.setRotation(c1angle);
				break;
			case player2:
				c2angle += Double.parseDouble(angleInput.getText());
				c2angle = Math.round(c2angle*100)/100.0d;
				c2angle %= 90;
				angleLabel.setText("Angle: "+String.valueOf(c2angle));
				game.cannon2.setRotation(180-c2angle);
			}
		else if(e.getSource() == speedMinus)
			switch(player)
			{
			case player1:
				c1speed -= Double.parseDouble(speedInput.getText());
				c1speed = Math.round(c1speed*100)/100.0d;
				if(c1speed < 0) c1speed = 0;
				speedLabel.setText("Speed: "+String.valueOf(c1speed));
				break;
			case player2:
				c2speed -= Double.parseDouble(speedInput.getText());
				c2speed = Math.round(c2speed*100)/100.0d;
				if(c2speed < 0) c2speed = 0;
				speedLabel.setText("Speed: "+String.valueOf(c2speed));
			}
		else if(e.getSource() == speedPlus)
			switch(player)
			{
			case player1:
				c1speed += Double.parseDouble(speedInput.getText());
				c1speed = Math.round(c1speed*100)/100.0d;
				c2speed = Math.round(c2speed*100)/100.0d;
				speedLabel.setText("Speed: "+String.valueOf(c1speed));
				break;
			case player2:
				c2speed += Double.parseDouble(speedInput.getText());
				speedLabel.setText("Speed: "+String.valueOf(c2speed));
			}
		else if(e.getSource() == fire && !gameEnd)
		{
			if(!game.timer.isRunning())
			switch(player)
			{
			case player1:
				game.player = player1;
				game.ball1 = new RigidBody();
				game.ball1.setSize(new Vector2i(20,20));
				game.ball1.setPosition(game.cannon1.getCornerPoint().x +( game.cannon1P.xpoints[2]-game.cannon1P.xpoints[1])/2,game.cannon1.getCornerPoint().y +(game.cannon1P.ypoints[2]-game.cannon1P.ypoints[1])/2);
				game.ball1.setOrigin(game.ball1.getSize().x/2,game.ball1.getSize().y/2);
				game.ball1.setRotation(c1angle);
				game.ball1.setAbsVelocity((float)c1speed*20);
				game.playerBall = game.ball1;
				turn1++;
				game.timer.restart();
				
				break;
			case player2:
				game.player = player2;
				game.ball2 = new RigidBody();
				game.ball2.setSize(new Vector2i(20,20));
				game.ball2.setPosition(game.cannon2.getCornerPoint().x +( game.cannon2P.xpoints[2]-game.cannon2P.xpoints[1])/2,game.cannon2.getCornerPoint().y +(game.cannon2P.ypoints[2]-game.cannon2P.ypoints[1])/2);
				game.ball2.setOrigin(game.ball2.getSize().x/2,game.ball2.getSize().y/2);
				game.ball2.setRotation(180-c2angle);
				game.ball2.setAbsVelocity((float)c2speed*20);
				game.playerBall = game.ball2;
				turn2++;
				game.timer.restart();
			
			}
			gui.hide();
			if (player == player1)
			{
				player = player2;
				this.angleLabel.setText("Angle: "+String.valueOf(180-game.cannon2.getAngle()));
				this.speedLabel.setText("Speed: "+String.valueOf(c2speed));
			}
			else
			{
				player = player1;
				this.angleLabel.setText("Angle: "+String.valueOf(game.cannon1.getAngle()));
				this.speedLabel.setText("Speed: "+String.valueOf(c1speed));
			}
			this.speedInput.setText("0");
			this.angleInput.setText("0");
			this.playerLabel.setText("Player"+String.valueOf(player));
		}
		else if (e.getSource() == fire && gameEnd)
		{
			gameEnd = false;
			gameTimer.start();
			game.timer.restart();
			game.cannon1 = new RigidBody();
			game.cannon1.setSize(100, 30);
			game.cannon1.setOrigin(30, 20);
			game.cannon1.setPosition((float)(50+Math.random()*200),game.ground.getCornerPoint().y-game.cannon1.getSize().y/2-10);
			game.cannon1LegPoints = new Vector2f((game.cannon1.getPosition().x +game.cannon1.getOrigin().x), (int)(game.cannon1.getPosition().y + game.cannon1.getOrigin().y));
			game.cannon1.setSize(100, 30);
			game.cannon1.setOrigin(30, 20);
			game.cannon1P = game.constructPolygon(game.cannon1);
			
			game.cannon2 = new RigidBody();
			game.cannon2.setSize(100, 30);
			game.cannon2.setOrigin(30, 10);
			game.cannon2.setPosition((float)(1150-Math.random()*200),game.ground.getCornerPoint().y-game.cannon2.getSize().y/2);
			game.cannon2LegPoints = new Vector2f((game.cannon2.getPosition().x +game.cannon2.getOrigin().x), (int)(game.cannon2.getPosition().y + game.cannon2.getOrigin().y));
			game.cannon2.setRotation(180);
			game.cannon2P = game.constructPolygon(game.cannon2);
			fire.setText("Fire!");
			playerLabel.setText("Player"+String.valueOf(player));
		}
			
		if(game.cannon1 != null)game.cannon1P = game.constructPolygon(game.cannon1);
		if(game.cannon2 != null)game.cannon2P = game.constructPolygon(game.cannon2);
		game.repaint();
	}
	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			if(!game.timer.isRunning())
				{
					gui.show();
				}
			if((game.cannon1 == null && !gameEnd) || (game.cannon2 == null && !gameEnd))
			{
				gameEnd = true;
				gui.show();
				fire.setText("New Game");
				if(game.cannon1 != null)
				{
					statsString = String.valueOf(gameRound++)+") Player1\n";
					playerLabel.setText("Player1 WINS!");
					score1 += 50/turn1;
				}
				else 
				{
					statsString = String.valueOf(gameRound++)+") Player2\n";
					playerLabel.setText("Player2 WINS!");
					score2 += 50/turn2;
				}
				game.windSpeed = "";
				stats.setText(stats.getText()+statsString);
				game.cannon1.setAbsVelocity(0.0f);
				game.cannon2.setAbsVelocity(0.0f);
				turn1 = turn2 = 1;
				game.score1 = String.valueOf(score1);
				game.score2 = String.valueOf(score2);
			}
		}
	}
}
