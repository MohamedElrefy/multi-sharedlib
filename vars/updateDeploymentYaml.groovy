def call(String projectDir, String fileName, String imageName, String buildNumber, String branchName) {
    stage('Update deployment.yaml') {
        dir(projectDir) {
            sh """
              
                sed -i 's|image: .*|image: ${imageName}:${buildNumber}|g' ${fileName}

               
                if grep -q 'namespace:' ${fileName}; then
                    sed -i 's|namespace: .*|namespace: ${branchName}|g' ${fileName}
                else
                   
                    awk -v ns="${branchName}" '/metadata:/{print; print "  namespace: " ns; next}1' ${fileName} > tmp && mv tmp ${fileName}
                fi

                echo "✅ Updated deployment file:"
                cat ${fileName}
            """
        }
    }
}
