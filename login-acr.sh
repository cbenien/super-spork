
az acr credential show -n chrisb \
    --query "join(' ', ['kubectl create secret docker-registry registrykey --docker-server chrisb.azurecr.io', '--docker-username', username, '--docker-password', passwords[0].value, '--docker-email example@example.com'])" \
    --output tsv\
    | sh


