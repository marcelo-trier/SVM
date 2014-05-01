package br.svm.ui;


import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.EmptyBorder;

import br.svm.Classe;



public class JanelaPrincipal extends JFrame {

	private JDesktopPane contentPane;
	private ButtonGroup group;

	ArrayList<Point> pontos = new ArrayList<Point>();

	public void clickTeste() {
	}
	
	public BufferedImage mix( BufferedImage imgArr[] ) {
		int w = imgArr[ 0 ].getWidth();
		int h = imgArr[ 0 ].getHeight();
		BufferedImage out = new BufferedImage( w, h, imgArr[0].getType() );
		WritableRaster outRaster = out.getRaster();		
		int[] pix = { 0, 0, 0, 255 };
		for( BufferedImage img : imgArr ) {
			Raster raster = img.getData();
			try {
			for( int y=0; y<h; y++ ) {
				for( int x=0; x<w; x++ ) {
					pix = raster.getPixel( x, y, pix );
					int soma = pix[0]+pix[1]+pix[2];
					if( soma > 5 )
						outRaster.setPixel( x, y, pix );
				}
			}
			
			} catch( Exception ex ) {
				int aa = 0;
				aa++;
			}			
		}
		
		return out;
	}
	
	
	public void clickDefinePontos( Classe umaC ) {
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		pontos.clear();
		ti.registraPonto(30, pontos, umaC );
		ti.atualizaPontos();
	}
	
	public void clickGeraArquivoSvm( Classe umaClasse  ) {
/*		Knn knn = new Knn( getImage(), umaClasse );
		KNNTypes t = getKnnType();
		knn.setN( t );
		//knn.init();
		knn.execute();
		mostraImagem( knn.geraImagem() ); */
	}
	
	public void clickMostrePontos() {
		//Point p = new Point( 0, 0 );
		Classe classesPontos[] = Classe.values();
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		for( int c=0; c<classesPontos.length; c++ ) { // for para todas as classes de pontos
			/*int[] arrX = Classe.PONTOSX[ c ];
			int[] arrY = Classe.PONTOSY[ c ];*/
			int[] arrX = Classe.PONTOSSS[0][ c ];
			int[] arrY = Classe.PONTOSSS[1][ c ];
			int numeroPontos = arrX.length;
			pontos.clear();
			for( int i=0; i<numeroPontos; i++ ) { // para a classe em questão irá pegar 30 pontos... para as outras, vai pegar os 1os 10.
				Point p = new Point( arrX[ i ], arrY[ i ] );
				pontos.add( p );
			}
			ti.mostrePontos( 30, pontos, classesPontos[ c ] );
		}
	}
	
	public void clickLimpaPontos() {
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		ti.pintaImagem();
		pontos.clear();
	}
	
	public void clickLimpaPontos__OLD() {
		String px = "int x[] = { ";
		String py = "int y[] = {";
		for( Point p : pontos ) {
			//px += "new Point(" + p.x + "," + p.y + "), ";
			px += p.x + ", ";
			py += p.y + ", ";
		}
		px += "}";
		py += "}";
		String str = px + "  " + py;
		//String str = px ;
		JOptionPane.showInputDialog("Copie: ", str );
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		ti.pintaImagem();
	}

	
	public void mostraImagem( String titulo, BufferedImage imgOut ) {
		TelaInterna interno = new TelaInterna( titulo, imgOut );
		contentPane.add( interno );
		interno.setVisible( true );	
		
	}
	
	public void mostraImagem( BufferedImage imgOut ) {
		mostraImagem( "", imgOut );
	}

	public BufferedImage getImage() {
		// pega a janela ativa...
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		BufferedImage img;
		img = ti.getImage();
		return img;
	}

	public void clickSave() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType( JFileChooser.SAVE_DIALOG );
		File umDir = new File( System.getProperty( "user.dir" ) );
		fileChooser.setCurrentDirectory( umDir );
		if( fileChooser.showSaveDialog( this ) != JFileChooser.APPROVE_OPTION ) {
			return;
		}
		File salvar = fileChooser.getSelectedFile();
		//ImageIO.w
		BufferedImage img = getImage();
		ImageIO.write( img, "bmp", salvar );
	}

	
	public void clickOnLoad() throws Exception {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType( JFileChooser.OPEN_DIALOG );
		File umDir = new File( System.getProperty( "user.dir" ) );
		fileChooser.setCurrentDirectory( umDir );
		if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File file = fileChooser.getSelectedFile();
		BufferedImage imagem = ImageIO.read( file );
		TelaInterna interno = new TelaInterna( imagem );
		contentPane.add( interno );
		interno.setVisible( true );
	}
	
	/**
	 * Create the frame.
	 */
	public JanelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 399);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Load...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickOnLoad();
				} catch( Exception ex ) {
					
				}
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save...");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickSave();
				} catch (Exception ex ) {
					
				}
			}
		});
		mnFile.add(mntmSave);
		
		JMenu mnImagens = new JMenu("Imagens");
		menuBar.add(mnImagens);
		
		JMenuItem mntmTeste = new JMenuItem("Teste");
		mntmTeste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickTeste();
			}
		});
		mnImagens.add(mntmTeste);
		
		JMenu mnProcessamento = new JMenu("Processamento");
		menuBar.add(mnProcessamento);
		
		JMenuItem mntmLimpapontos = new JMenuItem("LimpaPontos");
		mntmLimpapontos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickLimpaPontos();
			}
		});
		
		JMenu mnDefineClassePontos = new JMenu("Define Pontos(Classe)");
		mnProcessamento.add(mnDefineClassePontos);
		mnProcessamento.add(mntmLimpapontos);
		
		JMenuItem mntmMostrePontos = new JMenuItem("Mostre Pontos");
		mntmMostrePontos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickMostrePontos();
			}
		});
		mnProcessamento.add(mntmMostrePontos);
		
		JMenu menuGeraSvmFile = new JMenu("Gera Arquivo SVM (??)");
		mnProcessamento.add(menuGeraSvmFile);
		
		JMenuItem mntmGeraTodosArquivos = new JMenuItem("Gera Todos Arquivos");
		mnProcessamento.add(mntmGeraTodosArquivos);
		
		JMenuItem mntmCarregaArquivoProcessado = new JMenuItem("Carrega Arquivo Processado...");
		mnProcessamento.add(mntmCarregaArquivoProcessado);
		
		JMenuItem mntmMix = new JMenuItem("Mix");
		mnProcessamento.add(mntmMix);
		
		ActionListener alMenu = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for( Classe umaClasse : Classe.values() ) {
					if( e.getActionCommand().equals( umaClasse.toString() ) )
						clickGeraArquivoSvm( umaClasse );
				}
			}
		};
		
		ActionListener acaoMenuPontos = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				for( Classe umaC : Classe.values() ) {
					if( e.getActionCommand().equals( umaC.toString() ) )
						clickDefinePontos( umaC );
				}
			}
		};
		
		Classe asClasses[] = Classe.values();
		JMenuItem umMenu;
		JMenuItem menuPontos;
		for( Classe umaClasse : asClasses ) {
			umMenu = new JMenuItem( umaClasse.name() );
			umMenu.addActionListener( alMenu );
			menuGeraSvmFile.add( umMenu );
			
			menuPontos = new JMenuItem( umaClasse.name() );
			menuPontos.addActionListener( acaoMenuPontos );
			mnDefineClassePontos.add( menuPontos );
			
		}
		
		contentPane = new JDesktopPane();
		contentPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}

