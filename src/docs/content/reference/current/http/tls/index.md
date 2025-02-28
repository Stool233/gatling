---
title: "TLS"
description: "Use TLS to do HTTPS with Gatling"
lead: "Configure the SSLContext, SNI, keystore and truststore"
date: 2021-04-20T18:30:56+02:00
lastmod: 2021-04-20T18:30:56+02:00
weight: 2050400
---

## SSLContext

By default, each virtual user will have its own `SSLContext` and `SSLSession`.
This behavior is realistic when it comes to simulating web traffic so your server has to deal with the proper number of `SSLSession`.

You can only have a shared `SSLContext` if you decide to [shareConnections]({{< ref "../protocol#connection-sharing" >}}).

## Disabling OpenSSL

By default, Gatling uses [BoringSSL](https://opensource.google.com/projects/boringssl) (Google' fork of OpenSSL) to perform TLS.
This implementation is more efficient than the JDK's one, especially on JDK8.
It's also the only supported solution for HTTP/2 in Gatling with JDK8.

If you want to revert to using JDK's implementation, you can set the `gatling.http.ahc.useOpenSsl` property to `false` in `gatling.conf`

## Disabling SNI

By default, since JDK7, JDK enables [SNI](http://en.wikipedia.org/wiki/Server_Name_Indication) by default.
This can cause TLS handshake exceptions, such as `handshake alert:  unrecognized_name` when server names are not properly configured on the server side.
Browsers are more loose than JDK regarding this.

If you want to disable SNI, you can set the `gatling.http.ahc.enableSni` property to `false` in `gatling.conf`.

## TLSv1.3

Gatling supports TLSv1.3 as long as your Java version supports it as well, which means running **at least 1.8.0_262**.
TLSv1.3 is enabled by default.

## Configuring KeyStore and TrustStore

Default Gatling TrustStore is very permissive and doesn't validate certificates,
meaning that it works out of the box with self-signed certificates.

You can pass your own keystore and truststore in `gatling.conf`.

[perUserKeyManagerFactory]({{< ref "../protocol#keymanagerfactory" >}}) can be used to set the `KeyManagerFactory` for each virtual user.
