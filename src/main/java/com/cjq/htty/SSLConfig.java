package com.cjq.htty;

import java.io.File;

/**
 * A class that encapsulates SSLContext configuration.
 */
public class SSLConfig {
    private final File keyStore;
    private final String keyStorePassword;
    private final String certificatePassword;
    private final File trustKeyStore;
    private final String trustKeyStorePassword;

    private SSLConfig(File keyStore, String keyStorePassword,
                      String certificatePassword, File trustKeyStore, String trustKeyStorePassword) {
        this.keyStore = keyStore;
        this.keyStorePassword = keyStorePassword;
        this.certificatePassword = certificatePassword;
        this.trustKeyStore = trustKeyStore;
        this.trustKeyStorePassword = trustKeyStorePassword;
    }

    /**
     * @return KeyStore file
     */
    public File getKeyStore() {
        return keyStore;
    }

    /**
     * @return KeyStore password.
     */
    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    /**
     * @return certificate password
     */
    public String getCertificatePassword() {
        return certificatePassword;
    }

    /**
     * @return trust KeyStore file
     */
    public File getTrustKeyStore() {
        return trustKeyStore;
    }

    /**
     * @return trust KeyStore password.
     */
    public String getTrustKeyStorePassword() {
        return trustKeyStorePassword;
    }

    /**
     * @return instance of {@code Builder}
     */
    public static Builder builder(File keyStore, String keyStorePassword) {
        return new Builder(keyStore, keyStorePassword);
    }

    /**
     * Builder to help create the SSLConfig.
     */
    public static class Builder {
        private final File keyStore;
        private final String keyStorePassword;
        private String certificatePassword;
        private File trustKeyStore;
        private String trustKeyStorePassword;

        private Builder(File keyStore, String keyStorePassword) {
            this.keyStore = keyStore;
            this.keyStorePassword = keyStorePassword;
        }

        /**
         * Set the certificate password for KeyStore.
         *
         * @param certificatePassword certificate password
         * @return instance of {@code Builder}.
         */
        public Builder setCertificatePassword(String certificatePassword) {
            this.certificatePassword = certificatePassword;
            return this;
        }

        /**
         * Set trust KeyStore file.
         *
         * @param trustKeyStore trust KeyStore file.
         * @return an instance of {@code Builder}.
         */
        public Builder setTrustKeyStore(File trustKeyStore) {
            this.trustKeyStore = trustKeyStore;
            return this;
        }

        /**
         * Set trust KeyStore password.
         *
         * @param trustKeyStorePassword trust KeyStore password.
         * @return an instance of {@code Builder}.
         */
        public Builder setTrustKeyStorePassword(String trustKeyStorePassword) {
            if (trustKeyStorePassword == null) {
                throw new IllegalArgumentException("KeyStore Password Not Configured");
            }
            this.trustKeyStorePassword = trustKeyStorePassword;
            return this;
        }

        /**
         * @return instance of {@code SSLConfig}
         */
        public SSLConfig build() {
            if (keyStore == null) {
                throw new IllegalArgumentException("Certificate File Not Configured");
            }
            if (keyStorePassword == null) {
                throw new IllegalArgumentException("KeyStore Password Not Configured");
            }
            return new SSLConfig(keyStore, keyStorePassword, certificatePassword, trustKeyStore, trustKeyStorePassword);
        }
    }
}
