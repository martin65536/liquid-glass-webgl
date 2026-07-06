package defpackage;

import android.graphics.Typeface;
import android.util.Log;
import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rt implements ay, wn0, v51 {
    public final /* synthetic */ int e;

    public /* synthetic */ rt(int i) {
        this.e = i;
    }

    public static final float f(float f, float[] fArr, float[] fArr2) {
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float abs = Math.abs(f);
        float signum = Math.signum(f);
        int binarySearch = Arrays.binarySearch(fArr, abs);
        if (binarySearch >= 0) {
            return signum * fArr2[binarySearch];
        }
        int i = -(binarySearch + 1);
        int i2 = i - 1;
        if (i2 >= fArr.length - 1) {
            float f7 = fArr[fArr.length - 1];
            float f8 = fArr2[fArr.length - 1];
            if (f7 == 0.0f) {
                return 0.0f;
            }
            return (f8 / f7) * f;
        }
        if (i2 == -1) {
            float f9 = fArr[0];
            f4 = fArr2[0];
            f5 = f9;
            f3 = 0.0f;
            f2 = 0.0f;
        } else {
            float f10 = fArr[i2];
            float f11 = fArr[i];
            f2 = fArr2[i2];
            f3 = f10;
            f4 = fArr2[i];
            f5 = f11;
        }
        if (f3 == f5) {
            f6 = 0.0f;
        } else {
            f6 = (abs - f3) / (f5 - f3);
        }
        return (((f4 - f2) * Math.max(0.0f, Math.min(1.0f, f6))) + f2) * signum;
    }

    public static final void g(rt rtVar) {
        ky0 ky0Var;
        wl0 wl0Var;
        int i;
        Object obj;
        wl0 wl0Var2;
        ky0 ky0Var2 = to0.z;
        do {
            ky0Var = to0.z;
            wl0Var = (wl0) ky0Var.getValue();
            ml0 ml0Var = wl0Var.g;
            f90 f90Var = (f90) ml0Var.get(rtVar);
            if (f90Var == null) {
                wl0Var2 = wl0Var;
            } else {
                Object obj2 = f90Var.a;
                Object obj3 = f90Var.b;
                a31 a31Var = ml0Var.e;
                if (rtVar != null) {
                    i = rtVar.hashCode();
                } else {
                    i = 0;
                }
                a31 v = a31Var.v(i, 0, rtVar);
                if (a31Var != v) {
                    if (v == null) {
                        ml0Var = ml0.g;
                    } else {
                        ml0Var = new ml0(v, ml0Var.f - 1);
                    }
                }
                x1 x1Var = x1.G;
                if (obj2 != x1Var) {
                    Object obj4 = ml0Var.get(obj2);
                    obj4.getClass();
                    ml0Var = ml0Var.a(obj2, new f90(((f90) obj4).a, obj3));
                }
                if (obj3 != x1Var) {
                    Object obj5 = ml0Var.get(obj3);
                    obj5.getClass();
                    ml0Var = ml0Var.a(obj3, new f90(obj2, ((f90) obj5).b));
                }
                if (obj2 != x1Var) {
                    obj = wl0Var.e;
                } else {
                    obj = obj3;
                }
                if (obj3 != x1Var) {
                    obj2 = wl0Var.f;
                }
                wl0Var2 = new wl0(obj, obj2, ml0Var);
            }
            if (wl0Var == wl0Var2) {
                return;
            }
        } while (!ky0Var.j(wl0Var, wl0Var2));
    }

    @Override // defpackage.v51
    public s51 a(Class cls) {
        throw new UnsupportedOperationException("`Factory.create(String, CreationExtras)` is not implemented. You may need to override the method and provide a custom implementation. Note that using `Factory.create(String)` is not supported and considered an error.");
    }

    @Override // defpackage.v51
    public s51 b(Class cls, ee0 ee0Var) {
        a(cls);
        throw null;
    }

    @Override // defpackage.v51
    public s51 c(wd wdVar, ee0 ee0Var) {
        return new ls0();
    }

    @Override // defpackage.wn0
    public void d() {
        switch (this.e) {
            case 20:
                return;
            default:
                Log.d("ProfileInstaller", "DIAGNOSTIC_PROFILE_IS_COMPRESSED");
                return;
        }
    }

    @Override // defpackage.wn0
    public void e(int i, Object obj) {
        String str;
        switch (this.e) {
            case 20:
                return;
            default:
                switch (i) {
                    case 1:
                        str = "RESULT_INSTALL_SUCCESS";
                        break;
                    case 2:
                        str = "RESULT_ALREADY_INSTALLED";
                        break;
                    case 3:
                        str = "RESULT_UNSUPPORTED_ART_VERSION";
                        break;
                    case 4:
                        str = "RESULT_NOT_WRITABLE";
                        break;
                    case 5:
                        str = "RESULT_DESIRED_FORMAT_UNSUPPORTED";
                        break;
                    case 6:
                        str = "RESULT_BASELINE_PROFILE_NOT_FOUND";
                        break;
                    case 7:
                        str = "RESULT_IO_EXCEPTION";
                        break;
                    case 8:
                        str = "RESULT_PARSE_EXCEPTION";
                        break;
                    case 9:
                    default:
                        str = "";
                        break;
                    case 10:
                        str = "RESULT_INSTALL_SKIP_FILE_SUCCESS";
                        break;
                    case 11:
                        str = "RESULT_DELETE_SKIP_FILE_SUCCESS";
                        break;
                }
                if (i != 6 && i != 7 && i != 8) {
                    Log.d("ProfileInstaller", str);
                    return;
                } else {
                    Log.e("ProfileInstaller", str, (Throwable) obj);
                    return;
                }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Typeface h(nu nuVar, int i) {
        Typeface create;
        Object[] objArr;
        Object[] objArr2;
        boolean z = true;
        int i2 = 1;
        switch (this.e) {
            case 18:
                if (i == 0 && o20.e(nuVar, nu.g)) {
                    return Typeface.DEFAULT;
                }
                Typeface typeface = Typeface.DEFAULT;
                int i3 = nuVar.e;
                if (i != 1) {
                    z = false;
                }
                create = Typeface.create(typeface, i3, z);
                return create;
            default:
                if (i == 0 && o20.e(nuVar, nu.g)) {
                    return Typeface.DEFAULT;
                }
                if (o20.i(nuVar.e, nu.f.e) >= 0) {
                    objArr = true;
                } else {
                    objArr = false;
                }
                if (i == 1) {
                    objArr2 = true;
                } else {
                    objArr2 = false;
                }
                if (objArr2 != false && objArr != false) {
                    i2 = 3;
                } else if (objArr == false) {
                    if (objArr2 != false) {
                        i2 = 2;
                    } else {
                        i2 = 0;
                    }
                }
                return Typeface.defaultFromStyle(i2);
        }
    }

    public int i() {
        switch (this.e) {
            case 13:
                return 16;
            default:
                return 8;
        }
    }

    public boolean l(bd0 bd0Var) {
        switch (this.e) {
            case 13:
                return true;
            default:
                return o20.v(t20.d(k81.E(bd0Var), false));
        }
    }

    private final void j() {
    }

    private final void k(int i, Object obj) {
    }
}
