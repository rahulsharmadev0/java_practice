# Move WAR (override if exists)

sudo mv -f *.war "$WILDFLY_HOME/standalone/deployments/"


# Convert localhost to IP

### XML-based
Replace: 127.0.0.1 => 0.0.0.0  | or custom private ip of same network
```
<interfaces>
    <interface name="management">
        <inet-address value="${jboss.bind.address.management:127.0.0.1}"/>
    </interface>
    <interface name="public">
        <inet-address value="${jboss.bind.address:127.0.0.1}"/>
    </interface>
</interfaces>
```

### Quick for Once

Specific IP only: standalone.sh -b 192.168.5.221

For all interfaces (Public): standalone.sh -b 0.0.0.0




