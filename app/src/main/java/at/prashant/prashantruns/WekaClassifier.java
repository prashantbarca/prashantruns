package at.prashant.prashantruns;

class WekaClassifier {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier.N7c668a200(i);
        return p;
    }
    static double N7c668a200(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 82.931707) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 82.931707) {
            p = WekaClassifier.N139c7afa1(i);
        }
        return p;
    }
    static double N139c7afa1(Object []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 1;
        } else if (((Double) i[64]).doubleValue() <= 7.149756) {
            p = WekaClassifier.N4b5cce7d2(i);
        } else if (((Double) i[64]).doubleValue() > 7.149756) {
            p = WekaClassifier.N6eda57e10(i);
        }
        return p;
    }
    static double N4b5cce7d2(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 1;
        } else if (((Double) i[10]).doubleValue() <= 6.247228) {
            p = WekaClassifier.N1bc1677c3(i);
        } else if (((Double) i[10]).doubleValue() > 6.247228) {
            p = WekaClassifier.N31a0e6c79(i);
        }
        return p;
    }
    static double N1bc1677c3(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 4.376185) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() > 4.376185) {
            p = WekaClassifier.N53bed9ae4(i);
        }
        return p;
    }
    static double N53bed9ae4(Object []i) {
        double p = Double.NaN;
        if (i[17] == null) {
            p = 0;
        } else if (((Double) i[17]).doubleValue() <= 0.300527) {
            p = 0;
        } else if (((Double) i[17]).doubleValue() > 0.300527) {
            p = WekaClassifier.N238532895(i);
        }
        return p;
    }
    static double N238532895(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 1;
        } else if (((Double) i[3]).doubleValue() <= 33.615208) {
            p = WekaClassifier.Ne873fa26(i);
        } else if (((Double) i[3]).doubleValue() > 33.615208) {
            p = 0;
        }
        return p;
    }
    static double Ne873fa26(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 8.316257) {
            p = WekaClassifier.N1e6d43897(i);
        } else if (((Double) i[7]).doubleValue() > 8.316257) {
            p = 2;
        }
        return p;
    }
    static double N1e6d43897(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 1;
        } else if (((Double) i[3]).doubleValue() <= 11.055066) {
            p = WekaClassifier.N3bbb9a438(i);
        } else if (((Double) i[3]).doubleValue() > 11.055066) {
            p = 1;
        }
        return p;
    }
    static double N3bbb9a438(Object []i) {
        double p = Double.NaN;
        if (i[21] == null) {
            p = 1;
        } else if (((Double) i[21]).doubleValue() <= 1.219155) {
            p = 1;
        } else if (((Double) i[21]).doubleValue() > 1.219155) {
            p = 2;
        }
        return p;
    }
    static double N31a0e6c79(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 219.913171) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 219.913171) {
            p = 2;
        }
        return p;
    }
    static double N6eda57e10(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 601.183898) {
            p = WekaClassifier.N72e8cf2111(i);
        } else if (((Double) i[0]).doubleValue() > 601.183898) {
            p = 2;
        }
        return p;
    }
    static double N72e8cf2111(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 211.702339) {
            p = WekaClassifier.N15e63abd12(i);
        } else if (((Double) i[0]).doubleValue() > 211.702339) {
            p = WekaClassifier.N23c6601813(i);
        }
        return p;
    }
    static double N15e63abd12(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 24.771734) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() > 24.771734) {
            p = 2;
        }
        return p;
    }
    static double N23c6601813(Object []i) {
        double p = Double.NaN;
        if (i[11] == null) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() <= 11.727379) {
            p = 2;
        } else if (((Double) i[11]).doubleValue() > 11.727379) {
            p = WekaClassifier.N48ebf74b14(i);
        }
        return p;
    }
    static double N48ebf74b14(Object []i) {
        double p = Double.NaN;
        if (i[31] == null) {
            p = 0;
        } else if (((Double) i[31]).doubleValue() <= 7.683497) {
            p = 0;
        } else if (((Double) i[31]).doubleValue() > 7.683497) {
            p = 2;
        }
        return p;
    }
}
