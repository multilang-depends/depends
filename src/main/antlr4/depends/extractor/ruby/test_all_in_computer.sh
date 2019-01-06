for FILE in `cat filelist.txt`
do
echo $FILE  
./test.sh $FILE 
done
