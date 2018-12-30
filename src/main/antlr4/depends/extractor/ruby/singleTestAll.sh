for FILE in `ls *.rb`
do
echo $FILE; ./quicktest.sh $FILE
done
