package at.prashant.prashantruns;

class WekaClassifier {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier.N68fe3daa0(i);
        return p;
    }
    static double N68fe3daa0(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 115.375858) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 115.375858) {
            p = WekaClassifier.N5e95d07c1(i);
        }
        return p;
    }
    static double N5e95d07c1(Object []i) {
        double p = Double.NaN;
        if (i[64] == null) {
            p = 1;
        } else if (((Double) i[64]).doubleValue() <= 18.66042) {
            p = WekaClassifier.N6f9d226e2(i);
        } else if (((Double) i[64]).doubleValue() > 18.66042) {
            p = 2;
        }
        return p;
    }
    static double N6f9d226e2(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 155.46364) {
            p = WekaClassifier.N302b22dd3(i);
        } else if (((Double) i[0]).doubleValue() > 155.46364) {
            p = WekaClassifier.N59c779775(i);
        }
        return p;
    }
    static double N302b22dd3(Object []i) {
        double p = Double.NaN;
        if (i[18] == null) {
            p = 1;
        } else if (((Double) i[18]).doubleValue() <= 1.447001) {
            p = 1;
        } else if (((Double) i[18]).doubleValue() > 1.447001) {
            p = WekaClassifier.N29215a1f4(i);
        }
        return p;
    }
    static double N29215a1f4(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() <= 8.28217) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() > 8.28217) {
            p = 0;
        }
        return p;
    }
    static double N59c779775(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 7.82928) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() > 7.82928) {
            p = WekaClassifier.N5fcfbe736(i);
        }
        return p;
    }
    static double N5fcfbe736(Object []i) {
        double p = Double.NaN;
        if (i[31] == null) {
            p = 2;
        } else if (((Double) i[31]).doubleValue() <= 1.071337) {
            p = WekaClassifier.N369b429d7(i);
        } else if (((Double) i[31]).doubleValue() > 1.071337) {
            p = WekaClassifier.N64719d8a12(i);
        }
        return p;
    }
    static double N369b429d7(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() <= 5.595881) {
            p = WekaClassifier.N154917f38(i);
        } else if (((Double) i[5]).doubleValue() > 5.595881) {
            p = WekaClassifier.N55b9dc4d10(i);
        }
        return p;
    }
    static double N154917f38(Object []i) {
        double p = Double.NaN;
        if (i[32] == null) {
            p = 1;
        } else if (((Double) i[32]).doubleValue() <= 0.412569) {
            p = 1;
        } else if (((Double) i[32]).doubleValue() > 0.412569) {
            p = WekaClassifier.N77a7d8ec9(i);
        }
        return p;
    }
    static double N77a7d8ec9(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 269.061597) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() > 269.061597) {
            p = 1;
        }
        return p;
    }
    static double N55b9dc4d10(Object []i) {
        double p = Double.NaN;
        if (i[19] == null) {
            p = 2;
        } else if (((Double) i[19]).doubleValue() <= 0.234025) {
            p = WekaClassifier.N39dae3cd11(i);
        } else if (((Double) i[19]).doubleValue() > 0.234025) {
            p = 2;
        }
        return p;
    }
    static double N39dae3cd11(Object []i) {
        double p = Double.NaN;
        if (i[17] == null) {
            p = 1;
        } else if (((Double) i[17]).doubleValue() <= 0.565839) {
            p = 1;
        } else if (((Double) i[17]).doubleValue() > 0.565839) {
            p = 2;
        }
        return p;
    }
    static double N64719d8a12(Object []i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() <= 22.434977) {
            p = WekaClassifier.N2dd8495013(i);
        } else if (((Double) i[5]).doubleValue() > 22.434977) {
            p = 2;
        }
        return p;
    }
    static double N2dd8495013(Object []i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 136.626849) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() > 136.626849) {
            p = 2;
        }
        return p;
    }
}
