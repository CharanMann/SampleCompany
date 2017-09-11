#!/bin/bash
#
# Script to install/start/stop/restart SampleCompany services

# Install SampleCompany services
install() {
		cd /opt/forgerock/SampleCompany/CommonServices/
		/usr/bin/npm install &
		echo "********************** SampleCompany-CommonServices installed ***********************"

		cd /opt/forgerock/SampleCompany/EmployeeApp/
		/usr/bin/npm install &
		echo "********************** SampleCompany-EmployeeApp installed ***********************"

		cd /opt/forgerock/SampleCompany/TravelApp/
		/usr/bin/npm install &
		echo "********************** SampleCompany-TravelApp installed ***********************"

		cd /opt/forgerock/SampleCompany/CustomerApp/
		/usr/bin/npm install &
		echo "********************** SampleCompany-CustomerApp installed ***********************"

		cd /opt/forgerock/SampleCompany/BenefitsApp/
		mvn clean install
		echo "********************** SampleCompany-BenefitsApp installed ***********************"
}

# Start SampleCompany services
start() {
		cd /opt/forgerock/SampleCompany/CommonServices/
		/usr/bin/npm install &
		/usr/bin/node commonServices.js &
		echo "********************** SampleCompany-CommonServices started ***********************"

		cd /opt/forgerock/SampleCompany/EmployeeApp/
		/usr/bin/npm install &
		/usr/bin/node employeeApp.js &
		echo "********************** SampleCompany-EmployeeApp started ***********************"

		cd /opt/forgerock/SampleCompany/TravelApp/
		/usr/bin/npm install &
		/usr/bin/node travelApp.js &
		echo "********************** SampleCompany-TravelApp started ***********************"

		cd /opt/forgerock/SampleCompany/CustomerApp/
		/usr/bin/npm install &
		/usr/bin/node customerApp.js &
		echo "********************** SampleCompany-CustomerApp started ***********************"

		cd /opt/forgerock/SampleCompany/BenefitsApp/target
		java -jar BenefitsApp-1.0-jar-with-dependencies.jar &
		echo "********************** SampleCompany-BenefitsApp started ***********************"
}

# Stop SampleCompany services
stop() {
		cd /opt/forgerock/SampleCompany
		kill -9 $(ps -ef | grep 'Services.js' | awk '{print $2}')
        kill -9 $(ps -ef | grep 'App.js' | awk '{print $2}')
        kill -9 $(ps -ef | grep 'BenefitsApp' | awk '{print $2}')
        echo "********************** Sample Company apps stopped ***********************"
}

### services options ###
case "$1" in
	install)
        install
        ;;
  start)
        start
        ;;
  stop)
        stop
        ;;
  status)
        ps -ef|grep 'Services.js'
		ps -ef|grep 'App.js'
		ps -ef|grep BenefitsApp
        ;;
  restart)
        stop
        start
        ;;
  *)
        echo $"Usage: $0 {install|start|stop|restart|status}"
        exit 1
esac
exit 0
