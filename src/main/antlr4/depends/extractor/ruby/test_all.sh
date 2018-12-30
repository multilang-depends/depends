for FILE in `ls *.rb`
do
echo $FILE; ./test.sh $FILE
done
