def call(String projectDir, String fileName, String tokenId, String apiServerId, String branchName) {
    stage('Deploy to Kubernetes') {
        dir(projectDir) {
            withCredentials([
                string(credentialsId: tokenId, variable: 'TOKEN'),
                string(credentialsId: apiServerId, variable: 'APISERVER')
            ]) {
                sh """
                    echo "🚀 Deploying to namespace: ${branchName}"
                    kubectl --token="$TOKEN" \
                            --server="$APISERVER" \
                            --insecure-skip-tls-verify=true \
                            apply -n ${branchName} -f ${fileName}
                """
            }
        }
    }
}
