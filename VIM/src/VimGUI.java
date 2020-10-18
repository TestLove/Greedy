import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.System.exit;
import java.util.Scanner;
public class VimGUI {
//未复制就粘贴的错误,粘贴到下一行的错误,文件名只需要输入一次即可
//未能实现定时保存,远程操作和宏录制
    MyTextField mtf1 = new MyTextField();
    MyTextField mtf2 = new MyTextField();
    MyTextField mtf3 = new MyTextField();
    MyTextArea mta = new MyTextArea();
    JScrollPane jsp = new JScrollPane(mta);
    JPanel jp1 = new JPanel(new BorderLayout());
    JPanel jp2 = new JPanel(new GridLayout());
    MyFrame mf = new MyFrame(jp1,jp2,jsp,mtf1,mtf2,mtf3);
    boolean flag = true;// true为普通模式
    boolean flag1= false;// true为命令模式
    boolean flag2= false;
    boolean flag3=true;
    boolean flagchange = false;
    boolean c = true;
    String q=null;
    String former;
    String name=null;
    Scanner scan = new Scanner(System.in);

    FileOperation fo = new FileOperation();



    public static void main(String[] args) throws IOException, BadLocationException {
        VimGUI vim = new VimGUI();
        vim.mta.setEditable(false);
        vim.mtf2.setEditable(false);
        vim.mtf2.requestFocusInWindow();
        vim.former=vim.mta.getText();
        vim.Key();


    }
    public void Key() throws BadLocationException {


        mta.addKeyListener(new MyKey());
        mtf1.addKeyListener(new MyKey());
        mtf2.addKeyListener(new MyKey());
        mtf3.addKeyListener(new MyKey());
        mta.rows = mta.getLineOfOffset(mta.getCaretPosition());
        mtf3.setText(mta.rows+1+" "+mta.colums);
    }
    class MyKey implements KeyListener {


        @Override
        public void keyTyped(KeyEvent e) {
            int a = e.getKeyCode();
            //进入命令模式
            if(e.getKeyChar() ==':' &&flag==true&&flag1==false&&flag2==false&&flag3==true){
                flag =false;
                flag1=false;
                flag2=true;
                flag3=false;
                mtf1.setText(":") ;
                mtf1.requestFocusInWindow();
                mtf2.setEditable(false);
                mtf1.setEditable(true);
                c=true;
            }
            //字符串匹配
            if(e.getKeyChar() =='/' &&flag==true&&flag1==false&&flag2==false&&flag3==true)
            {
                flag =false;
                flag1=false;
                flag2=true;
                flag3=false;
                mtf1.setText("/") ;
                mtf1.requestFocusInWindow();
                mtf2.setEditable(false);
                mtf1.setEditable(true);
                c=true;
            }
            //进入输入模式
            if((e.getKeyChar()=='i'||e.getKeyChar()=='I') &&flag==true&&flag1==false&&flag2==false&&flag3==true){

                mtf2.setEditable(false);
                flag =false;
                flag1=true;
                flag2=false;
                flag3 = false;
                mtf1.setText("-- INSERT -- ") ;
                mta.setEditable(true);
                mta.requestFocusInWindow();
                c=true;
            }


        }


        public void keyPressed(KeyEvent e) {

            int a = e.getKeyCode();
            if(((e.getKeyCode()==KeyEvent.VK_LEFT||e.getKeyCode()==KeyEvent.VK_H)&&flag==true&&flag1==false&&flag2==false&&flag3==true)
                    ){mtf2.setEditable(false);
                mta.setCaretPosition(mta.getCaretPosition()-1);
                try {
                    mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                    mta.colums = mta.getCaretPosition()-mta.getLineStartOffset(mta.rows);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }

                c=true;
                System.out.println("L1");


            }
            //向右移
            if((e.getKeyCode()==KeyEvent.VK_L||e.getKeyCode()==KeyEvent.VK_RIGHT)&&flag==true&&flag1==false&&flag2==false&&flag3==true
                   ){mtf2.setEditable(false);
                mta.setCaretPosition(mta.getCaretPosition()+1);
                try {
                    mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                    mta.colums = mta.getCaretPosition()-mta.getLineStartOffset(mta.rows);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }

                System.out.println("R1");
                c=true;


            }
            //向下移
            if((e.getKeyCode()==KeyEvent.VK_J||e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_ENTER)&&flag==true&&flag1==false&&flag2==false&&flag3==true
                    ){ mtf2.setEditable(false);

                try {
                    mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                    mta.setCaretPosition(mta.getCaretPosition()+mta.getLineEndOffset(mta.rows)-mta.getLineStartOffset(mta.rows));
                    mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                    mta.colums = mta.getCaretPosition()-mta.getLineStartOffset(mta.rows);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }

                c=true;
                System.out.println("D1");

            }
            //向上移
            if((e.getKeyCode()==KeyEvent.VK_K||e.getKeyCode()==KeyEvent.VK_UP)&&flag==true&&flag1==false&&flag2==false&&flag3==true
                    ){mtf2.setEditable(false);
                try {
                    mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                    mta.setCaretPosition(mta.getCaretPosition()-mta.getLineEndOffset(mta.rows-1)+mta.getLineStartOffset(mta.rows-1));
                    mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                    mta.colums = mta.getCaretPosition()-mta.getLineStartOffset(mta.rows);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }

                c=true;
                System.out.println("U1");

            }
            //进入普通模式
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {


                flag =true;
                flag1=false;
                flag2=false;
                flag3=true;
                c=false;
                mtf1.setText("") ;
                mtf2.setText("");
//                mtf2.setEditable(false);
                mta.setEditable(false);
                mtf2.requestFocusInWindow();
                mtf2.setEditable(false);
            }
            //执行文件命令
            if(e.getKeyCode() == KeyEvent.VK_ENTER&&flag==false&&flag1==false&&flag2==true&&flag3==false) {
                String command = mtf1.getText();
                String contains = mta.getText();
                System.out.println(contains);
                c = true;
                //查找
                if (command.startsWith("/")) {
                    String pattern= command.substring(1);
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(contains);
                    if(m.find())
                    mta.setCaretPosition(m.start());
                    else
                    {
                        mtf1.setText("Not Found");
                    }

                } else {// 需要在控制台输入文件名才能保存
                    switch (command) {
                        case ":w":
                            try {
                                if(fo.name.equals("Untiled"))
                                { System.out.println("输入文件名");
                                    fo.name=scan.next();
                                }
                                fo.SaveFile(contains);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } finally {
                                mtf1.setText("");
                            }
                            break;
                        case ":q":
                            if(former.equals(mta.getText())){
                                exit(0);
                            }else{
                                mtf1.setText("文档已改变,需要保存");
                            }
                            break;
                        case ":q!":
                            exit(0);
                            break;
                        case ":x":
                            try {
                                if(fo.name.equals("Untiled"))
                                { System.out.println("输入文件名");
                                    fo.name=scan.next();
                                }
                                fo.SaveFile(contains);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            exit(0);
                            break;
                        default:
                            Pattern p1 = Pattern.compile(":%s/(.*?)/(.*)");
                            Matcher m1 = p1.matcher(command);

                            if(m1.find())
                            {   //System.out.println(m1.group(1)+"  "+m1.group(2));
                                Pattern p2 = Pattern.compile(m1.group(1));
                                Matcher m2 = p2.matcher(contains);
                                if(m2.find()){
                                    System.out.println("匹配成功");
                                    mta.setText(m2.replaceAll(m1.group(2)));
                                    break;
                                }else{
                                    mtf1.setText("Not Found");
                                    break;
                                }

                            }else{
                                System.out.println(999);
                            }

                            mtf1.setText("没有这个命令");
                            mta.requestFocusInWindow();
                            break;
                    }


                }
            }
            mtf3.setText(mta.rows+1+" "+mta.colums);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int a = e.getKeyCode();
            //输入模式下的操作
            if(flag==false&&flag1==true&&flag2==false&&flag3==false){
                c=true;
                if(a==KeyEvent.VK_LEFT){
                    mta.setCaretPosition(mta.getCaretPosition());
                    try {
                        mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                        mta.colums = mta.getCaretPosition()-mta.getLineStartOffset(mta.rows);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("L2");

                }
                else if(a==KeyEvent.VK_RIGHT){
                    mta.setCaretPosition(mta.getCaretPosition());
                    try {
                        mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                        mta.colums = mta.getCaretPosition()-mta.getLineStartOffset(mta.rows);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("R2");

                }
                else if(a==KeyEvent.VK_UP){
                    try {
                        mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                        mta.setCaretPosition(mta.getCaretPosition()-mta.getLineEndOffset(mta.rows-1)+mta.getLineStartOffset(mta.rows));
                        mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                        mta.colums = mta.getCaretPosition()-mta.getLineStartOffset(mta.rows);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }

                }
                else if(a==KeyEvent.VK_DOWN){

                    try {
                        mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                        mta.setCaretPosition(mta.getCaretPosition()+mta.getLineEndOffset(mta.rows)-mta.getLineStartOffset(mta.rows+1));
                        mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                        mta.colums = mta.getCaretPosition()-mta.getLineStartOffset(mta.rows);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }

                }else{
                    try {
                        mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                        mta.colums = mta.getCaretPosition()-mta.getLineStartOffset(mta.rows);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }

                }
                mtf3.setText(mta.rows+1+" "+mta.colums);
            }
           if(flag3==true&c==true){
               try {
                   System.out.println(mta.getLineStartOffset(mta.rows+1));
               } catch (BadLocationException ex) {
                   ex.printStackTrace();
               }
               mtf2.requestFocusInWindow();
               mtf2.setEditable(true);
               String s = mtf2.getText();
               String d = "hhhhhhh";
               switch(s){
                   //思路是把前后的字符串都存起来,再根据需求操作
                   case "dd":
                       int point=mta.getCaretPosition();
                       try {int x;
                           if(mta.rows==mta.getLineCount()-1)
                               x=mta.getLineEndOffset(mta.rows);//rows,offsets都是从零开始
                           else
                                x=mta.getLineStartOffset(mta.rows+1);
                           int y=mta.getLineEndOffset(mta.getLineCount()-1);
                           d=mta.getText(0,mta.getLineStartOffset(mta.rows))+mta.getText(x,y-x);
                           mta.rows= mta.getLineOfOffset(mta.getCaretPosition());
                           mta.colums = mta.getCaretPosition()-mta.getLineStartOffset(mta.rows);
                           mtf3.setText(mta.rows+1+" "+mta.colums);

                           mta.setText(d);
                       } catch (BadLocationException ex) {
                           ex.printStackTrace();
                       }
                       mtf2.setText("");
                       mta.setCaretPosition(point);
                       break;
                   case "yy":
                       //System.out.println(q+d);
                       try {
                           d = mta.getText(mta.getLineStartOffset(mta.rows),mta.getLineEndOffset(mta.rows)-mta.getLineStartOffset(mta.rows)-1);
                           q=d;
                           System.out.println(q+d);
                       } catch (BadLocationException ex) {
                           ex.printStackTrace();
                       }
                       mtf2.setText("");
                       System.out.println(q+d);
                       break;
                   case "p": mtf2.setText("");
                                int point1=mta.getCaretPosition();
                               int x;
                               try {
                               x=mta.getLineEndOffset(mta.rows);//rows,offsets都是从零开始
                               int y=mta.getLineEndOffset(mta.getLineCount()-1);
                               d=mta.getText(0,x-1)+q+'\n'+mta.getText(x,y-x);
                               System.out.println(d+"   "+q);
                           } catch (BadLocationException ex) {
                               ex.printStackTrace();

                           }
                               if(q!=null)
                                   mta.setText(d);
                               else
                                   mtf1.setText("NO Copy");
                               mta.setCaretPosition(point1);
                           break;
                    default:
                        if(s.length()>=2){
                       mtf2.setText("");
                       flag3 = false;
                       }



                   }
               }
           }


        }
    }


    class MyTextField extends JTextField {


        public MyTextField() {
            this.setBackground(Color.BLACK);
            this.setForeground(Color.white);
            this.setCaretColor(Color.white);
            this.setFont(new Font("楷体", Font.BOLD, 16));


        }


        private void addKeyListener() {
        }


    }
    class MyFrame extends JFrame {

        public MyFrame(JPanel jp1,JPanel jp2,JScrollPane jsp,MyTextField mtf1,MyTextField mtf2,MyTextField mtf3) {
            jp1.add(jsp,BorderLayout.CENTER);
            jp2.add(mtf1);
            jp2.add(mtf2);
            jp2.add(mtf3);
            jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER ) ;
            this.add(jp1,BorderLayout.CENTER);
            this.add(jp2,BorderLayout.SOUTH);
            this.setBounds(600, 100, 600, 600);
            this.setVisible(true);
            this.setDefaultCloseOperation(3);
        }
    }


