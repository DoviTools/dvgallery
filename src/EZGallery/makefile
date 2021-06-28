APP_ID=ezGallery

GRADLE=./gradlew

global: globalApk #archiveGlobal

clean:
	@echo "====> clean"
	@${GRADLE} clean

globalApk:
	@echo "====> build Global apk"
	@${GRADLE} ${APP_ID}:assembleRelease

    
archiveGlobal:
	@echo "====> archive Global"
	@${GRADLE} ${APP_ID}:archiveRelease


