package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class cz implements gv {
    public final /* synthetic */ int e = 0;
    public final /* synthetic */ int f;

    public /* synthetic */ cz(int i) {
        this.f = i;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        gv gvVar;
        switch (this.e) {
            case 0:
                byte[] bArr = (byte[]) obj;
                bArr.getClass();
                BitmapFactory.Options options = new BitmapFactory.Options();
                int i = this.f;
                if (160 > i) {
                    options.inDensity = 160;
                    options.inTargetDensity = i;
                }
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                decodeByteArray.getClass();
                return new zy(new o5(decodeByteArray));
            default:
                o60 o60Var = (o60) obj;
                ww0 t = t20.t();
                if (t != null) {
                    gvVar = t.e();
                } else {
                    gvVar = null;
                }
                t20.K(t, t20.C(t), gvVar);
                o60Var.getClass();
                return x31.a;
        }
    }

    public /* synthetic */ cz(m70 m70Var, int i) {
        this.f = i;
    }
}
