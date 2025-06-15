ASem:
clear && rm -fr ts.txt && java -jar procesadorPDL.jar PIdG10.txt pair.txt token.txt ts.txt

ALex:
clear && javac -d bin src/*.java && java -cp bin ALex PIdG10.txt token.txt

ASin:
clear && java -cp bin Ast PIdG10.txt parse.txt