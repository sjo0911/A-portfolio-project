# First stage for building project and generating apiman folder and policy 
From maven AS projectbuild
  #copy source code and recommanded tools 
COPY project_source_code  project_dir
WORKDIR project_dir
RUN mvn install #build your project at this l

# final build stage  copy resources from project build
FROM  jboss/wildfly:20.0.1.Final
COPY  --from=projectbuild apiman_folder ${JBOSS_HOME}
COPY  --from=projectbuild .m2/war_file_path(policy) <deployments_directory>
#EXPOSE 8080 9990
USER root

RUN chown -R jboss:0 ${JBOSS_HOME} \
 && chmod -R g+rw ${JBOSS_HOME} 

USER jboss
ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-c", "standalone-apiman.xml"]