#!/bin/bash
#
# Script to start/stop/restart SampleCompany services
start() {
	#/opt/forgerock/opendjis1/bin/start-ds
	cd /opt/forgerock/SampleCompany/CommonServices/
	node server.js &
	echo "********************** CommonServices started ***********************"
}
# Stop the OpenAM and depedent services
stop() {
        sudo service haproxy stop
        echo "********************** HAProxy stoped ***********************"

        sudo /etc/init.d/apache2 stop
        echo "********************** Apache2 stoped ***********************"

        /opt/forgerock/opendjis1/bin/stop-ds
        echo "********************** OpenDJ Identity Store stoped ***********************"

        /opt/forgerock/opendjcs1/bin/stop-ds
        echo "********************** OpenDJ Config Store stoped ***********************"

        /opt/forgerock/Openam1_instance/bin/shutdown.sh
        echo "********************** OpenAM instance stoped ***********************"
}

### services options ###
case "$1" in
  start)
        start
        ;;
  stop)
        stop
        ;;
  status)
        ps -ef|grep java
	ps -ef|grep apache2
	ps -ef|grep haproxy
        ;;
  restart)
        stop
        start
        ;;
  *)
        echo $"Usage: $0 {start|stop|restart|status}"
        exit 1
esac
exit 0
