connect('weblogic', 'Admin123', 't3://localhost:9500')
edit()
startEdit()
deploy('Socialize', '/app/oracle/ChitChatInstaller/war/Socialize.war', targets='bi_server1')
save()
activate()
exit()
