#!/usr/bin/env bash

controle=false

echo -e "\e[95mNome Da Branch: [Ss,Nn] - Default(S)"
read branch

if [ -z "$branch" ]
then
    controle=false
else
    controle=true
fi

echo $controle

case "$controle" in  
    "true")
	exit_branch=$(git ls-remote --heads https://github.com/adotando-pet/adotandopet-api.git $branch)
    
    if [ -z "$exit_branch" ]
    then
        git branch $branch
        git checkout $branch
        git add .
        git commit -m 'create branch'
        git push -u origin $branch
        
    else
        echo "Branch ja existe" $exit_branch
    fi
;;
*)
;;
esac