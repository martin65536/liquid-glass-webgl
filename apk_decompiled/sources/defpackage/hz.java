package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hz {
    public final String a;
    public final float b;
    public final float c;
    public final float d;
    public final float e;
    public final long f;
    public final int g;
    public final boolean h;
    public final ArrayList i;
    public final gz j;
    public boolean k;

    public hz(String str, float f, float f2, float f3, float f4, boolean z, int i) {
        boolean z2;
        str = (i & 1) != 0 ? "" : str;
        long j = se.g;
        if ((i & 128) != 0) {
            z2 = false;
        } else {
            z2 = z;
        }
        this.a = str;
        this.b = f;
        this.c = f2;
        this.d = f3;
        this.e = f4;
        this.f = j;
        this.g = 5;
        this.h = z2;
        ArrayList arrayList = new ArrayList();
        this.i = arrayList;
        gz gzVar = new gz(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, 1023);
        this.j = gzVar;
        arrayList.add(gzVar);
    }

    public final iz a() {
        if (this.k) {
            q00.b("ImageVector.Builder is single use, create a new instance to create a new ImageVector");
        }
        while (true) {
            ArrayList arrayList = this.i;
            if (arrayList.size() > 1) {
                if (this.k) {
                    q00.b("ImageVector.Builder is single use, create a new instance to create a new ImageVector");
                }
                gz gzVar = (gz) arrayList.remove(arrayList.size() - 1);
                ((gz) arrayList.get(arrayList.size() - 1)).j.add(new n41(gzVar.a, gzVar.b, gzVar.c, gzVar.d, gzVar.e, gzVar.f, gzVar.g, gzVar.h, gzVar.i, gzVar.j));
            } else {
                gz gzVar2 = this.j;
                iz izVar = new iz(this.a, this.b, this.c, this.d, this.e, new n41(gzVar2.a, gzVar2.b, gzVar2.c, gzVar2.d, gzVar2.e, gzVar2.f, gzVar2.g, gzVar2.h, gzVar2.i, gzVar2.j), this.f, this.g, this.h);
                this.k = true;
                return izVar;
            }
        }
    }
}
