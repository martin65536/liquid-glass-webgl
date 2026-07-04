package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RenderEffect;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.StrictMode;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.window.BackEvent;
import com.kyant.backdrop.catalog.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
public abstract class g30 {
    public static final long A(long j, float f) {
        if (!Float.isNaN(f) && f < 1.0f) {
            return se.b(j, se.d(j) * f);
        }
        return j;
    }

    public static final int B(String str) {
        if (str.startsWith("#")) {
            int length = str.length();
            if (length != 4) {
                if (length != 5) {
                    if (length != 7) {
                        if (length != 9) {
                            return -16777216;
                        }
                        return n30.J(str.substring(1));
                    }
                    return n30.J(str.substring(1)) | (-16777216);
                }
                int J = n30.J(str.substring(1));
                return ((J & 15) * 17) | (((J >> 12) & 15) * 285212672) | (((J >> 8) & 15) * 1114112) | (((J >> 4) & 15) * 4352) | (-16777216);
            }
            int J2 = n30.J(str.substring(1));
            return ((J2 & 15) * 17) | (((J2 >> 8) & 15) * 1114112) | (((J2 >> 4) & 15) * 4352) | (-16777216);
        }
        throw new IllegalArgumentException("Invalid color value ".concat(str).toString());
    }

    public static final float C(String str, mm mmVar) {
        mmVar.getClass();
        if (str == null) {
            return 0.0f;
        }
        if (bz0.z(str, "dp", false)) {
            return Float.parseFloat(uy0.D(str, "dp"));
        }
        if (bz0.z(str, "px", false)) {
            return mmVar.o0(Float.parseFloat(uy0.D(str, "px")));
        }
        throw new UnsupportedOperationException("value should ends with dp or px");
    }

    public static final int D(String str) {
        int hashCode = str.hashCode();
        if (hashCode != -1073910849) {
            if (hashCode != -436781190) {
                if (hashCode == 94742715 && str.equals("clamp")) {
                    return 0;
                }
            } else if (str.equals("repeated")) {
                return 1;
            }
        } else if (str.equals("mirror")) {
            return 2;
        }
        throw new UnsupportedOperationException("unknown tileMode: ".concat(str));
    }

    public static final long E(um0 um0Var, boolean z) {
        long f = ch0.f(um0Var.c, um0Var.g);
        if (!z && um0Var.b()) {
            return 0L;
        }
        return f;
    }

    public static final void F(np npVar, String str, String str2, gv gvVar) {
        RenderEffect createChainEffect;
        npVar.getClass();
        if (!y20.n()) {
            return;
        }
        h6 r = npVar.k.r(str, str2);
        gvVar.e(r);
        RenderEffect b = d1.b(r.a);
        b.getClass();
        e6 e6Var = new e6(b);
        gh ghVar = npVar.j;
        if (ghVar != null) {
            createChainEffect = RenderEffect.createChainEffect(e6Var.c(), ghVar.c());
            createChainEffect.getClass();
            e6Var = new e6(createChainEffect);
        }
        npVar.j = e6Var;
    }

    public static final void a(final cd0 cd0Var, m70 m70Var, final tj0 tj0Var, final y7 y7Var, z9 z9Var, rl rlVar, boolean z, e5 e5Var, final gv gvVar, bw bwVar, final int i) {
        final m70 m70Var2;
        final z9 z9Var2;
        final rl rlVar2;
        final boolean z2;
        final e5 e5Var2;
        m70 m70Var3;
        e5 e5Var3;
        int i2;
        int i3;
        rl rlVar3;
        e5 e5Var4;
        bwVar.W(53695811);
        int i4 = i | 46861328 | (bwVar.h(gvVar) ? 536870912 : 268435456);
        boolean z3 = true;
        if (bwVar.O(i4 & 1, (306783379 & i4) != 306783378)) {
            bwVar.S(-127, null, 0, null);
            int i5 = 18;
            if ((i & 1) != 0 && !bwVar.y()) {
                bwVar.R();
                i2 = i4 & (-238551153);
                m70Var3 = m70Var;
                z9Var2 = z9Var;
                rlVar3 = rlVar;
                z3 = z;
                i3 = 18;
                e5Var4 = e5Var;
            } else {
                h70 h70Var = o70.a;
                Object[] objArr = new Object[0];
                c4 c4Var = m70.x;
                boolean d = bwVar.d(0) | bwVar.d(0);
                Object L = bwVar.L();
                Object obj = ph.a;
                if (d || L == obj) {
                    L = new c2(i5);
                    bwVar.f0(L);
                }
                m70Var3 = (m70) y20.t(objArr, c4Var, (vu) L, bwVar, 0);
                z9 z9Var3 = x1.r;
                float f = yx0.a;
                mm mmVar = (mm) bwVar.j(fi.h);
                boolean c = bwVar.c(mmVar.B());
                Object L2 = bwVar.L();
                if (c || L2 == obj) {
                    L2 = new fl(new j2(mmVar));
                    bwVar.f0(L2);
                }
                fl flVar = (fl) L2;
                boolean f2 = bwVar.f(flVar);
                Object L3 = bwVar.L();
                if (f2 || L3 == obj) {
                    L3 = new rl(flVar);
                    bwVar.f0(L3);
                }
                rl rlVar4 = (rl) L3;
                gi giVar = kj0.a;
                bwVar.V(282942128);
                f5 f5Var = (f5) bwVar.j(kj0.a);
                if (f5Var == null) {
                    bwVar.p(false);
                    e5Var3 = null;
                } else {
                    boolean f3 = bwVar.f(f5Var);
                    Object L4 = bwVar.L();
                    if (f3 || L4 == obj) {
                        Object e5Var5 = new e5(f5Var.a, f5Var.b, f5Var.c, f5Var.d);
                        bwVar.f0(e5Var5);
                        L4 = e5Var5;
                    }
                    e5Var3 = (e5) L4;
                    bwVar.p(false);
                }
                i2 = i4 & (-238551153);
                i3 = 18;
                z9Var2 = z9Var3;
                rlVar3 = rlVar4;
                e5Var4 = e5Var3;
            }
            bwVar.q();
            y20.b(cd0Var, m70Var3, tj0Var, rlVar3, z3, e5Var4, z9Var2, y7Var, gvVar, bwVar, 806907270, 6 | ((i2 >> i3) & 7168));
            rlVar2 = rlVar3;
            z2 = z3;
            e5Var2 = e5Var4;
            m70Var2 = m70Var3;
        } else {
            bwVar.R();
            m70Var2 = m70Var;
            z9Var2 = z9Var;
            rlVar2 = rlVar;
            z2 = z;
            e5Var2 = e5Var;
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new kv(m70Var2, tj0Var, y7Var, z9Var2, rlVar2, z2, e5Var2, gvVar, i) { // from class: s50
                public final /* synthetic */ m70 f;
                public final /* synthetic */ tj0 g;
                public final /* synthetic */ y7 h;
                public final /* synthetic */ z9 i;
                public final /* synthetic */ rl j;
                public final /* synthetic */ boolean k;
                public final /* synthetic */ e5 l;
                public final /* synthetic */ gv m;

                @Override // defpackage.kv
                public final Object d(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int O = d20.O(24967);
                    g30.a(cd0.this, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, (bw) obj2, O);
                    return x31.a;
                }
            };
        }
    }

    public static final void b(gg ggVar, bw bwVar, int i) {
        boolean z;
        bwVar.W(-709502251);
        byte b = 0;
        if ((i & 3) != 2) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            do0 do0Var = gs0.a;
            Object obj = (es0) bwVar.j(do0Var);
            bwVar.V(1967007413);
            Object[] objArr = new Object[0];
            Object L = bwVar.L();
            Object obj2 = ph.a;
            if (L == obj2) {
                L = new c2(28);
                bwVar.f0(L);
            }
            ds0 ds0Var = (ds0) y20.t(objArr, ds0.i, (vu) L, bwVar, 384);
            ds0Var.g = (es0) bwVar.j(do0Var);
            bwVar.p(false);
            Object[] objArr2 = {obj};
            c4 c4Var = new c4(21, new w4(9, b), new c(13, obj, ds0Var));
            boolean h = bwVar.h(obj) | bwVar.h(ds0Var);
            Object L2 = bwVar.L();
            if (h || L2 == obj2) {
                L2 = new f9(4, obj, ds0Var);
                bwVar.f0(L2);
            }
            Object obj3 = (p70) y20.t(objArr2, c4Var, (vu) L2, bwVar, 0);
            o20.a(do0Var.a(obj3), jc0.C(-412824043, new eb(ggVar, obj3, 5), bwVar), bwVar, 56);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new wa(i, 3, ggVar);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void c(defpackage.vu r29, defpackage.m9 r30, defpackage.cd0 r31, boolean r32, long r33, long r35, defpackage.gg r37, defpackage.bw r38, int r39, int r40) {
        /*
            Method dump skipped, instructions count: 558
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.g30.c(vu, m9, cd0, boolean, long, long, gg, bw, int, int):void");
    }

    public static final void d(bw bwVar, int i) {
        boolean z;
        long j;
        long f;
        long f2;
        bwVar.W(862511212);
        boolean z2 = false;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            boolean D = n20.D(bwVar);
            if (!D) {
                j = se.b;
            } else {
                j = se.c;
            }
            long j2 = j;
            if (!D) {
                f = f31.f(4278225151L);
            } else {
                f = f31.f(4278227455L);
            }
            long j3 = f;
            if (!D) {
                f2 = f31.f(4294967295L);
            } else {
                f2 = f31.f(4279374354L);
            }
            f31.b(null, jc0.C(180178753, new xb0(f2, j2, j3), bwVar), bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new yu0(i, 11, z2);
        }
    }

    public static final rf0 e(BackEvent backEvent) {
        float touchX;
        float touchY;
        float progress;
        int swipeEdge;
        long j;
        touchX = backEvent.getTouchX();
        touchY = backEvent.getTouchY();
        progress = backEvent.getProgress();
        swipeEdge = backEvent.getSwipeEdge();
        if (Build.VERSION.SDK_INT >= 36) {
            j = backEvent.getFrameTimeMillis();
        } else {
            j = 0;
        }
        return new rf0(swipeEdge, progress, touchX, touchY, j);
    }

    public static final void f(bw bwVar, int i) {
        boolean z;
        long j;
        long f;
        bwVar.W(-1118208236);
        boolean z2 = false;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            boolean D = n20.D(bwVar);
            if (!D) {
                j = se.b;
            } else {
                j = se.c;
            }
            if (!D) {
                f = se.c;
            } else {
                f = f31.f(4286611584L);
            }
            f31.b(null, jc0.C(-159558039, new bo0(f, j), bwVar), bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new yu0(i, 13, z2);
        }
    }

    public static final void g(bw bwVar, int i) {
        boolean z;
        bwVar.W(-725296680);
        boolean z2 = false;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            f31.b(null, sg.a, bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new yu0(i, 14, z2);
        }
    }

    public static final void h(bw bwVar, int i) {
        boolean z;
        long f;
        bwVar.W(645273584);
        boolean z2 = false;
        int i2 = 1;
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            if (!n20.D(bwVar)) {
                f = f31.f(4294967295L);
            } else {
                f = f31.f(4279374354L);
            }
            f31.b(null, jc0.C(-165180795, new pw0(i2, f), bwVar), bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new yu0(i, 16, z2);
        }
    }

    public static final float i(dm0 dm0Var, boolean z, ty[] tyVarArr, float f) {
        boolean z2;
        float f2 = Float.NaN;
        for (ty tyVar : tyVarArr) {
            float u = dm0Var.u(tyVar);
            if (!Float.isNaN(f2)) {
                if (u > f2) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z != z2) {
                }
            }
            f2 = u;
        }
        if (Float.isNaN(f2)) {
            return f;
        }
        return f2;
    }

    public static final Bitmap j(Image image) {
        Image.Plane[] planes = image.getPlanes();
        planes.getClass();
        Image.Plane plane = planes[0];
        int height = image.getHeight() * image.getWidth();
        int[] iArr = new int[height];
        plane.getBuffer().asIntBuffer().get(iArr);
        for (int i = 0; i < height; i++) {
            int i2 = iArr[i];
            int i3 = ((i2 >> 24) & 255) << 24;
            iArr[i] = f31.P(f31.e(((i2 & 255) << 16) | i3 | (((i2 >> 8) & 255) << 8) | ((i2 >> 16) & 255)));
        }
        return Bitmap.createBitmap(iArr, image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
    }

    public static final int k(long[] jArr, long j) {
        int length = jArr.length - 1;
        int i = 0;
        while (i <= length) {
            int i2 = (i + length) >>> 1;
            long j2 = jArr[i2];
            if (j > j2) {
                i = i2 + 1;
            } else if (j < j2) {
                length = i2 - 1;
            } else {
                return i2;
            }
        }
        return -(i + 1);
    }

    public static final boolean l(um0 um0Var) {
        if (!um0Var.h && um0Var.d) {
            return true;
        }
        return false;
    }

    public static final boolean m(um0 um0Var) {
        if (!um0Var.b() && um0Var.h && !um0Var.d) {
            return true;
        }
        return false;
    }

    public static final boolean n(um0 um0Var) {
        if (um0Var.h && !um0Var.d) {
            return true;
        }
        return false;
    }

    public static boolean o(File file, InputStream inputStream) {
        FileOutputStream fileOutputStream;
        StrictMode.ThreadPolicy allowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(file, false);
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    try {
                        break;
                    } catch (IOException unused) {
                    }
                }
            }
            fileOutputStream.close();
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
            return true;
        } catch (IOException e2) {
            e = e2;
            fileOutputStream2 = fileOutputStream;
            Log.e("TypefaceCompatUtil", "Error copying resource contents to temp file: " + e.getMessage());
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused2) {
                }
            }
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException unused3) {
                }
            }
            StrictMode.setThreadPolicy(allowThreadDiskWrites);
            throw th;
        }
    }

    public static final void p(yj yjVar) {
        d30 d30Var = (d30) yjVar.j(x1.L);
        if (d30Var != null && !d30Var.b()) {
            throw d30Var.m();
        }
    }

    public static final j80 q(View view) {
        j80 j80Var;
        view.getClass();
        while (view != null) {
            Object tag = view.getTag(R.id.view_tree_lifecycle_owner);
            if (tag instanceof j80) {
                j80Var = (j80) tag;
            } else {
                j80Var = null;
            }
            if (j80Var != null) {
                return j80Var;
            }
            Object j = y20.j(view);
            if (j instanceof View) {
                view = (View) j;
            } else {
                view = null;
            }
        }
        return null;
    }

    public static final d30 s(yj yjVar) {
        d30 d30Var = (d30) yjVar.j(x1.L);
        if (d30Var != null) {
            return d30Var;
        }
        v7.e(yjVar, "Current context doesn't contain Job in it: ");
        return null;
    }

    public static File t(Context context) {
        File cacheDir = context.getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        String str = ".font" + Process.myPid() + "-" + Process.myTid() + "-";
        for (int i = 0; i < 100; i++) {
            File file = new File(cacheDir, str + i);
            if (file.createNewFile()) {
                return file;
            }
        }
        return null;
    }

    public static final boolean u(Spanned spanned, Class cls) {
        if (spanned.nextSpanTransition(-1, spanned.length(), cls) != spanned.length()) {
            return true;
        }
        return false;
    }

    public static final un v(d30 d30Var, boolean z, h30 h30Var) {
        if (d30Var instanceof l30) {
            return ((l30) d30Var).U(z, h30Var);
        }
        return d30Var.y(h30Var.r(), z, new e(1, h30Var, h30.class, "invoke", "invoke(Ljava/lang/Throwable;)V", 0, 0, 1));
    }

    public static final boolean w(yj yjVar) {
        d30 d30Var = (d30) yjVar.j(x1.L);
        if (d30Var != null) {
            return d30Var.b();
        }
        return true;
    }

    public static final boolean x(um0 um0Var, long j, long j2) {
        int i;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = false;
        if (um0Var.i == 1) {
            i = 1;
        } else {
            i = 0;
        }
        long j3 = um0Var.c;
        float intBitsToFloat = Float.intBitsToFloat((int) (j3 >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j3 & 4294967295L));
        float f = i;
        float intBitsToFloat3 = Float.intBitsToFloat((int) (j2 >> 32)) * f;
        float f2 = ((int) (j >> 32)) + intBitsToFloat3;
        float intBitsToFloat4 = Float.intBitsToFloat((int) (j2 & 4294967295L)) * f;
        float f3 = ((int) (j & 4294967295L)) + intBitsToFloat4;
        if (intBitsToFloat < (-intBitsToFloat3)) {
            z = true;
        } else {
            z = false;
        }
        if (intBitsToFloat > f2) {
            z2 = true;
        } else {
            z2 = false;
        }
        boolean z5 = z2 | z;
        if (intBitsToFloat2 < (-intBitsToFloat4)) {
            z3 = true;
        } else {
            z3 = false;
        }
        boolean z6 = z5 | z3;
        if (intBitsToFloat2 > f3) {
            z4 = true;
        }
        return z6 | z4;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [ij, mv0, java.lang.Object] */
    public static mv0 y(kv kvVar) {
        ?? obj = new Object();
        obj.g = t20.o(obj, obj, kvVar);
        return obj;
    }

    public static MappedByteBuffer z(Context context, Uri uri) {
        ParcelFileDescriptor openFileDescriptor;
        try {
            openFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r", null);
        } catch (IOException unused) {
        }
        if (openFileDescriptor == null) {
            if (openFileDescriptor != null) {
                openFileDescriptor.close();
                return null;
            }
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(openFileDescriptor.getFileDescriptor());
            try {
                FileChannel channel = fileInputStream.getChannel();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                fileInputStream.close();
                openFileDescriptor.close();
                return map;
            } finally {
            }
        } finally {
        }
    }

    public abstract wo0 r();
}
