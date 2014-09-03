prg="$0"
prgdir=`dirname "$prg"`
cd "$prgdir"
date=`date +%Y%m%d`
agloco_file='agloco'$date'.sql'
mysql_file='mysql'$date'.sql'
mysqldump -uagloco_dumpa -pdump#agl0c0@! agloco > $agloco_file
mysqldump -uroot -pagl0c0 mysql > $mysql_file
tgzfile='agloco'$date'.tgz'
tar czf $tgzfile $agloco_file
chmod 400 $tgzfile
mv $tgzfile backup/
tgzfile='mysql'$date'.tgz'
tar czf $tgzfile $mysql_file
chmod 400 $tgzfile
mv $tgzfile backup/
rm -f $agloco_file
rm -f $mysql_file
