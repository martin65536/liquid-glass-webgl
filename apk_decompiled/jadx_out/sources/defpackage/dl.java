package defpackage;

import android.R;
import android.view.DragEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class dl {
    public static final qt j;
    public static final c4 t;
    public static final c4 u;
    public static final e7 a = new e7(Float.POSITIVE_INFINITY);
    public static final f7 b = new f7(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    public static final g7 c = new g7(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    public static final h7 d = new h7(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    public static final e7 e = new e7(Float.NEGATIVE_INFINITY);
    public static final f7 f = new f7(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
    public static final g7 g = new g7(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
    public static final h7 h = new h7(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
    public static final nd i = new Object();
    public static final tn k = new Object();
    public static final c61 l = new c61(0.31006f, 0.31616f);
    public static final c61 m = new c61(0.34567f, 0.3585f);
    public static final c61 n = new c61(0.32168f, 0.33767f);
    public static final c61 o = new c61(0.31271f, 0.32902f);
    public static final float[] p = {0.964212f, 1.0f, 0.825188f};
    public static final byte[] q = {112, 114, 111, 0};
    public static final byte[] r = {112, 114, 109, 0};
    public static final x11 s = new x11(0, new long[0], new Object[0]);

    /* JADX WARN: Type inference failed for: r0v10, types: [java.lang.Object, tn] */
    /* JADX WARN: Type inference failed for: r0v8, types: [nd, java.lang.Object] */
    static {
        int i2 = 7;
        j = new qt(i2);
        int i3 = 23;
        t = new c4(i3, new m41(0), new m41(17));
        new c4(i3, new m41(1), new m41(2));
        new c4(i3, new m41(3), new m41(4));
        new c4(i3, new m41(5), new m41(6));
        new c4(i3, new m41(i2), new m41(8));
        u = new c4(i3, new m41(9), new m41(10));
        new c4(i3, new m41(11), new m41(12));
        new c4(i3, new m41(13), new m41(14));
        new c4(i3, new m41(15), new m41(16));
    }

    public static final cd0 A(bw bwVar, cd0 cd0Var) {
        if (cd0Var.d(w3.o)) {
            return cd0Var;
        }
        bwVar.S(1219399079, null, 0, null);
        cd0 cd0Var2 = (cd0) cd0Var.a(new o(4, bwVar), zc0.a);
        bwVar.p(false);
        return cd0Var2;
    }

    public static final cd0 B(bw bwVar, cd0 cd0Var) {
        bwVar.V(439770924);
        cd0 A = A(bwVar, cd0Var);
        bwVar.p(false);
        return A;
    }

    public static final cd0 C(cd0 cd0Var, float f2) {
        return cd0Var.b(new rj0(f2, f2, f2, f2));
    }

    public static cd0 D(cd0 cd0Var, float f2) {
        return cd0Var.b(new rj0(f2, 0.0f, f2, 0.0f));
    }

    public static final cd0 E(cd0 cd0Var, float f2, float f3, float f4, float f5) {
        return cd0Var.b(new rj0(f2, f3, f4, f5));
    }

    public static cd0 F(cd0 cd0Var, float f2, int i2) {
        float f3;
        if ((i2 & 2) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 8) != 0) {
            f3 = 0.0f;
        } else {
            f3 = 72.0f;
        }
        return E(cd0Var, 0.0f, f2, 0.0f, f3);
    }

    public static final pk G(pt ptVar) {
        int ordinal = ptVar.I0().ordinal();
        pk pkVar = pk.e;
        if (ordinal != 0) {
            pk pkVar2 = null;
            pk pkVar3 = pk.f;
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal != 3) {
                        v7.k();
                        return null;
                    }
                } else {
                    return pkVar3;
                }
            } else {
                pt v = n20.v(ptVar);
                if (v != null) {
                    pk G = G(v);
                    if (G != pkVar) {
                        pkVar2 = G;
                    }
                    if (pkVar2 == null) {
                        if (!ptVar.t) {
                            ptVar.t = true;
                            try {
                                mt F0 = ptVar.F0();
                                lt ltVar = (lt) ((b4) k81.F(ptVar)).getFocusOwner();
                                pt f2 = ltVar.f();
                                F0.k.getClass();
                                pt f3 = ltVar.f();
                                if (f2 != f3 && f3 != null) {
                                    if (nt.d == nt.c) {
                                        return pkVar3;
                                    }
                                    return pk.g;
                                }
                                return pkVar;
                            } finally {
                                ptVar.t = false;
                            }
                        }
                        return pkVar;
                    }
                    return pkVar2;
                }
                v7.m("ActiveParent with no focused child");
                return null;
            }
        }
        return pkVar;
    }

    public static final pk H(pt ptVar) {
        if (!ptVar.u) {
            ptVar.u = true;
            try {
                mt F0 = ptVar.F0();
                lt ltVar = (lt) ((b4) k81.F(ptVar)).getFocusOwner();
                pt f2 = ltVar.f();
                F0.j.getClass();
                pt f3 = ltVar.f();
                if (f2 != f3 && f3 != null) {
                    if (nt.d == nt.c) {
                        return pk.f;
                    }
                    return pk.g;
                }
            } finally {
                ptVar.u = false;
            }
        }
        return pk.e;
    }

    public static final pk I(pt ptVar) {
        bd0 bd0Var;
        lg0 lg0Var;
        int ordinal = ptVar.I0().ordinal();
        pk pkVar = pk.e;
        if (ordinal != 0) {
            pk pkVar2 = null;
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal == 3) {
                        if (!ptVar.e.r) {
                            q00.b("visitAncestors called on an unattached node");
                        }
                        bd0 bd0Var2 = ptVar.e.i;
                        z40 E = k81.E(ptVar);
                        loop0: while (true) {
                            if (E != null) {
                                if ((E.H.f.h & 1024) != 0) {
                                    while (bd0Var2 != null) {
                                        if ((bd0Var2.g & 1024) != 0) {
                                            bd0Var = bd0Var2;
                                            ef0 ef0Var = null;
                                            while (bd0Var != null) {
                                                if (bd0Var instanceof pt) {
                                                    break loop0;
                                                }
                                                if ((bd0Var.g & 1024) != 0 && (bd0Var instanceof jm)) {
                                                    int i2 = 0;
                                                    for (bd0 bd0Var3 = ((jm) bd0Var).t; bd0Var3 != null; bd0Var3 = bd0Var3.j) {
                                                        if ((bd0Var3.g & 1024) != 0) {
                                                            i2++;
                                                            if (i2 == 1) {
                                                                bd0Var = bd0Var3;
                                                            } else {
                                                                if (ef0Var == null) {
                                                                    ef0Var = new ef0(new bd0[16]);
                                                                }
                                                                if (bd0Var != null) {
                                                                    ef0Var.b(bd0Var);
                                                                    bd0Var = null;
                                                                }
                                                                ef0Var.b(bd0Var3);
                                                            }
                                                        }
                                                    }
                                                    if (i2 == 1) {
                                                    }
                                                }
                                                bd0Var = k81.h(ef0Var);
                                            }
                                        }
                                        bd0Var2 = bd0Var2.i;
                                    }
                                }
                                E = E.s();
                                if (E != null && (lg0Var = E.H) != null) {
                                    bd0Var2 = lg0Var.e;
                                } else {
                                    bd0Var2 = null;
                                }
                            } else {
                                bd0Var = null;
                                break;
                            }
                        }
                        pt ptVar2 = (pt) bd0Var;
                        if (ptVar2 == null) {
                            return pkVar;
                        }
                        int ordinal2 = ptVar2.I0().ordinal();
                        if (ordinal2 != 0) {
                            if (ordinal2 != 1) {
                                if (ordinal2 != 2) {
                                    if (ordinal2 == 3) {
                                        pk I = I(ptVar2);
                                        if (I != pkVar) {
                                            pkVar2 = I;
                                        }
                                        if (pkVar2 == null) {
                                            return H(ptVar2);
                                        }
                                        return pkVar2;
                                    }
                                    v7.k();
                                    return null;
                                }
                                return pk.f;
                            }
                            return I(ptVar2);
                        }
                        return H(ptVar2);
                    }
                    v7.k();
                    return null;
                }
            } else {
                pt v = n20.v(ptVar);
                if (v != null) {
                    return G(v);
                }
                v7.m("ActiveParent with no focused child");
                return null;
            }
        }
        return pkVar;
    }

    public static int[] J(ByteArrayInputStream byteArrayInputStream, int i2) {
        int[] iArr = new int[i2];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 += (int) n20.L(byteArrayInputStream, 2);
            iArr[i4] = i3;
        }
        return iArr;
    }

    public static an[] K(FileInputStream fileInputStream, byte[] bArr, byte[] bArr2, an[] anVarArr) {
        byte[] bArr3 = n20.l;
        if (Arrays.equals(bArr, bArr3)) {
            if (!Arrays.equals(n20.g, bArr2)) {
                if (Arrays.equals(bArr, bArr3)) {
                    int L = (int) n20.L(fileInputStream, 1);
                    byte[] K = n20.K(fileInputStream, (int) n20.L(fileInputStream, 4), (int) n20.L(fileInputStream, 4));
                    if (fileInputStream.read() <= 0) {
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(K);
                        try {
                            an[] L2 = L(byteArrayInputStream, L, anVarArr);
                            byteArrayInputStream.close();
                            return L2;
                        } catch (Throwable th) {
                            try {
                                byteArrayInputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    }
                    v7.o("Content found after the end of file");
                    return null;
                }
                v7.o("Unsupported meta version");
                return null;
            }
            v7.o("Requires new Baseline Profile Metadata. Please rebuild the APK with Android Gradle Plugin 7.2 Canary 7 or higher");
            return null;
        }
        if (Arrays.equals(bArr, n20.m)) {
            int L3 = (int) n20.L(fileInputStream, 2);
            byte[] K2 = n20.K(fileInputStream, (int) n20.L(fileInputStream, 4), (int) n20.L(fileInputStream, 4));
            if (fileInputStream.read() <= 0) {
                ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(K2);
                try {
                    an[] M = M(byteArrayInputStream2, bArr2, L3, anVarArr);
                    byteArrayInputStream2.close();
                    return M;
                } catch (Throwable th3) {
                    try {
                        byteArrayInputStream2.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                    throw th3;
                }
            }
            v7.o("Content found after the end of file");
            return null;
        }
        v7.o("Unsupported meta version");
        return null;
    }

    public static an[] L(ByteArrayInputStream byteArrayInputStream, int i2, an[] anVarArr) {
        if (byteArrayInputStream.available() == 0) {
            return new an[0];
        }
        if (i2 == anVarArr.length) {
            String[] strArr = new String[i2];
            int[] iArr = new int[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                int L = (int) n20.L(byteArrayInputStream, 2);
                iArr[i3] = (int) n20.L(byteArrayInputStream, 2);
                strArr[i3] = new String(n20.J(byteArrayInputStream, L), StandardCharsets.UTF_8);
            }
            for (int i4 = 0; i4 < i2; i4++) {
                an anVar = anVarArr[i4];
                if (anVar.b.equals(strArr[i4])) {
                    int i5 = iArr[i4];
                    anVar.e = i5;
                    anVar.h = J(byteArrayInputStream, i5);
                } else {
                    v7.o("Order of dexfiles in metadata did not match baseline");
                    return null;
                }
            }
            return anVarArr;
        }
        v7.o("Mismatched number of dex files found in metadata");
        return null;
    }

    public static an[] M(ByteArrayInputStream byteArrayInputStream, byte[] bArr, int i2, an[] anVarArr) {
        String str;
        an anVar;
        if (byteArrayInputStream.available() == 0) {
            return new an[0];
        }
        if (i2 == anVarArr.length) {
            for (int i3 = 0; i3 < i2; i3++) {
                n20.L(byteArrayInputStream, 2);
                String str2 = new String(n20.J(byteArrayInputStream, (int) n20.L(byteArrayInputStream, 2)), StandardCharsets.UTF_8);
                long L = n20.L(byteArrayInputStream, 4);
                int L2 = (int) n20.L(byteArrayInputStream, 2);
                if (anVarArr.length > 0) {
                    int indexOf = str2.indexOf("!");
                    if (indexOf < 0) {
                        indexOf = str2.indexOf(":");
                    }
                    if (indexOf > 0) {
                        str = str2.substring(indexOf + 1);
                    } else {
                        str = str2;
                    }
                    for (int i4 = 0; i4 < anVarArr.length; i4++) {
                        if (anVarArr[i4].b.equals(str)) {
                            anVar = anVarArr[i4];
                            break;
                        }
                    }
                }
                anVar = null;
                if (anVar != null) {
                    anVar.d = L;
                    int[] J = J(byteArrayInputStream, L2);
                    if (Arrays.equals(bArr, n20.k)) {
                        anVar.e = L2;
                        anVar.h = J;
                    }
                } else {
                    v7.o("Missing profile key: ".concat(str2));
                    return null;
                }
            }
            return anVarArr;
        }
        v7.o("Mismatched number of dex files found in metadata");
        return null;
    }

    public static an[] N(FileInputStream fileInputStream, byte[] bArr, String str) {
        if (Arrays.equals(bArr, n20.h)) {
            int L = (int) n20.L(fileInputStream, 1);
            byte[] K = n20.K(fileInputStream, (int) n20.L(fileInputStream, 4), (int) n20.L(fileInputStream, 4));
            if (fileInputStream.read() <= 0) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(K);
                try {
                    an[] O = O(byteArrayInputStream, str, L);
                    byteArrayInputStream.close();
                    return O;
                } catch (Throwable th) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            v7.o("Content found after the end of file");
            return null;
        }
        v7.o("Unsupported version");
        return null;
    }

    public static an[] O(ByteArrayInputStream byteArrayInputStream, String str, int i2) {
        int i3;
        int i4 = 0;
        if (byteArrayInputStream.available() == 0) {
            return new an[0];
        }
        an[] anVarArr = new an[i2];
        for (int i5 = 0; i5 < i2; i5++) {
            int L = (int) n20.L(byteArrayInputStream, 2);
            int L2 = (int) n20.L(byteArrayInputStream, 2);
            anVarArr[i5] = new an(str, new String(n20.J(byteArrayInputStream, L), StandardCharsets.UTF_8), n20.L(byteArrayInputStream, 4), L2, (int) n20.L(byteArrayInputStream, 4), (int) n20.L(byteArrayInputStream, 4), new int[L2], new TreeMap());
        }
        int i6 = 0;
        while (i6 < i2) {
            an anVar = anVarArr[i6];
            int available = byteArrayInputStream.available();
            int i7 = anVar.f;
            int i8 = anVar.g;
            TreeMap treeMap = anVar.i;
            int i9 = available - i7;
            int i10 = i4;
            while (byteArrayInputStream.available() > i9) {
                i10 += (int) n20.L(byteArrayInputStream, 2);
                treeMap.put(Integer.valueOf(i10), 1);
                int L3 = (int) n20.L(byteArrayInputStream, 2);
                while (L3 > 0) {
                    n20.L(byteArrayInputStream, 2);
                    int L4 = (int) n20.L(byteArrayInputStream, 1);
                    if (L4 != 6 && L4 != 7) {
                        while (L4 > 0) {
                            n20.L(byteArrayInputStream, 1);
                            int i11 = i4;
                            int i12 = i6;
                            for (int L5 = (int) n20.L(byteArrayInputStream, 1); L5 > 0; L5--) {
                                n20.L(byteArrayInputStream, 2);
                            }
                            L4--;
                            i4 = i11;
                            i6 = i12;
                        }
                    }
                    L3--;
                    i4 = i4;
                    i6 = i6;
                }
            }
            int i13 = i4;
            int i14 = i6;
            if (byteArrayInputStream.available() == i9) {
                anVar.h = J(byteArrayInputStream, anVar.e);
                BitSet valueOf = BitSet.valueOf(n20.J(byteArrayInputStream, (((i8 * 2) + 7) & (-8)) / 8));
                for (int i15 = i13; i15 < i8; i15++) {
                    if (valueOf.get(i15)) {
                        i3 = 2;
                    } else {
                        i3 = i13;
                    }
                    if (valueOf.get(i15 + i8)) {
                        i3 |= 4;
                    }
                    if (i3 != 0) {
                        Integer num = (Integer) treeMap.get(Integer.valueOf(i15));
                        if (num == null) {
                            num = Integer.valueOf(i13);
                        }
                        treeMap.put(Integer.valueOf(i15), Integer.valueOf(i3 | num.intValue()));
                    }
                }
                i6 = i14 + 1;
                i4 = i13;
            } else {
                v7.o("Read too much data during profile line parse");
                return null;
            }
        }
        return anVarArr;
    }

    public static final gf P(m9 m9Var, m9 m9Var2, bw bwVar, int i2) {
        boolean z;
        m9Var.getClass();
        m9Var2.getClass();
        boolean z2 = false;
        if ((((i2 & 14) ^ 6) > 4 && bwVar.f(m9Var)) || (i2 & 6) == 4) {
            z = true;
        } else {
            z = false;
        }
        if ((((i2 & 112) ^ 48) > 32 && bwVar.f(m9Var2)) || (i2 & 48) == 32) {
            z2 = true;
        }
        boolean z3 = z | z2;
        Object L = bwVar.L();
        if (z3 || L == ph.a) {
            L = new gf(m9Var, m9Var2);
            bwVar.f0(L);
        }
        return (gf) L;
    }

    public static cd0 Q(cd0 cd0Var, m70 m70Var, dj0 dj0Var, e5 e5Var, boolean z, rl rlVar, je0 je0Var) {
        cd0 l2;
        dj0 dj0Var2 = dj0.e;
        zc0 zc0Var = zc0.a;
        if (dj0Var == dj0Var2) {
            l2 = n20.l(zc0Var, uy.c);
        } else {
            l2 = n20.l(zc0Var, uy.b);
        }
        return cd0Var.b(l2).b(new ot0(e5Var, rlVar, je0Var, dj0Var, m70Var, z, false));
    }

    public static String R(String str) {
        int hashCode = str.hashCode();
        switch (hashCode) {
            case -2061550653:
                if (!str.equals("kotlin.jvm.internal.DoubleCompanionObject")) {
                    return null;
                }
                return "Companion";
            case -2056817302:
                if (str.equals("java.lang.Integer")) {
                    return "Int";
                }
                return null;
            case -2034166429:
                if (str.equals("java.lang.Cloneable")) {
                    return "Cloneable";
                }
                return null;
            case -1979556166:
                if (str.equals("java.lang.annotation.Annotation")) {
                    return "Annotation";
                }
                return null;
            case -1571515090:
                if (str.equals("java.lang.Comparable")) {
                    return "Comparable";
                }
                return null;
            case -1383349348:
                if (str.equals("java.util.Map")) {
                    return "Map";
                }
                return null;
            case -1383343454:
                if (str.equals("java.util.Set")) {
                    return "Set";
                }
                return null;
            case -1325958191:
                if (str.equals("double")) {
                    return "Double";
                }
                return null;
            case -1182275604:
                if (!str.equals("kotlin.jvm.internal.ByteCompanionObject")) {
                    return null;
                }
                return "Companion";
            case -1062240117:
                if (str.equals("java.lang.CharSequence")) {
                    return "CharSequence";
                }
                return null;
            case -688322466:
                if (str.equals("java.util.Collection")) {
                    return "Collection";
                }
                return null;
            case -527879800:
                if (str.equals("java.lang.Float")) {
                    return "Float";
                }
                return null;
            case -515992664:
                if (str.equals("java.lang.Short")) {
                    return "Short";
                }
                return null;
            case -246476834:
                if (!str.equals("kotlin.jvm.internal.CharCompanionObject")) {
                    return null;
                }
                return "Companion";
            case -207262728:
                if (!str.equals("kotlin.jvm.internal.LongCompanionObject")) {
                    return null;
                }
                return "Companion";
            case -165139126:
                if (str.equals("java.util.Map$Entry")) {
                    return "Entry";
                }
                return null;
            case 104431:
                if (str.equals("int")) {
                    return "Int";
                }
                return null;
            case 3039496:
                if (str.equals("byte")) {
                    return "Byte";
                }
                return null;
            case 3052374:
                if (str.equals("char")) {
                    return "Char";
                }
                return null;
            case 3327612:
                if (str.equals("long")) {
                    return "Long";
                }
                return null;
            case 64711720:
                if (str.equals("boolean")) {
                    return "Boolean";
                }
                return null;
            case 65821278:
                if (str.equals("java.util.List")) {
                    return "List";
                }
                return null;
            case 77230534:
                if (!str.equals("kotlin.jvm.internal.ShortCompanionObject")) {
                    return null;
                }
                return "Companion";
            case 97526364:
                if (str.equals("float")) {
                    return "Float";
                }
                return null;
            case 109413500:
                if (str.equals("short")) {
                    return "Short";
                }
                return null;
            case 155276373:
                if (str.equals("java.lang.Character")) {
                    return "Char";
                }
                return null;
            case 226173651:
                if (!str.equals("kotlin.jvm.internal.EnumCompanionObject")) {
                    return null;
                }
                return "Companion";
            case 344809556:
                if (str.equals("java.lang.Boolean")) {
                    return "Boolean";
                }
                return null;
            case 398507100:
                if (str.equals("java.lang.Byte")) {
                    return "Byte";
                }
                return null;
            case 398585941:
                if (str.equals("java.lang.Enum")) {
                    return "Enum";
                }
                return null;
            case 398795216:
                if (str.equals("java.lang.Long")) {
                    return "Long";
                }
                return null;
            case 482629606:
                if (!str.equals("kotlin.jvm.internal.FloatCompanionObject")) {
                    return null;
                }
                return "Companion";
            case 499831342:
                if (str.equals("java.util.Iterator")) {
                    return "Iterator";
                }
                return null;
            case 577341676:
                if (str.equals("java.util.ListIterator")) {
                    return "ListIterator";
                }
                return null;
            case 599019395:
                if (!str.equals("kotlin.jvm.internal.StringCompanionObject")) {
                    return null;
                }
                return "Companion";
            case 761287205:
                if (str.equals("java.lang.Double")) {
                    return "Double";
                }
                return null;
            case 1052881309:
                if (str.equals("java.lang.Number")) {
                    return "Number";
                }
                return null;
            case 1063877011:
                if (str.equals("java.lang.Object")) {
                    return "Any";
                }
                return null;
            case 1195259493:
                if (str.equals("java.lang.String")) {
                    return "String";
                }
                return null;
            case 1275614662:
                if (str.equals("java.lang.Iterable")) {
                    return "Iterable";
                }
                return null;
            case 1383693018:
                if (!str.equals("kotlin.jvm.internal.BooleanCompanionObject")) {
                    return null;
                }
                return "Companion";
            case 1630335596:
                if (str.equals("java.lang.Throwable")) {
                    return "Throwable";
                }
                return null;
            case 1877171123:
                if (!str.equals("kotlin.jvm.internal.IntCompanionObject")) {
                    return null;
                }
                return "Companion";
            default:
                switch (hashCode) {
                    case -1811142716:
                        if (str.equals("kotlin.jvm.functions.Function10")) {
                            return "Function10";
                        }
                        return null;
                    case -1811142715:
                        if (str.equals("kotlin.jvm.functions.Function11")) {
                            return "Function11";
                        }
                        return null;
                    case -1811142714:
                        if (str.equals("kotlin.jvm.functions.Function12")) {
                            return "Function12";
                        }
                        return null;
                    case -1811142713:
                        if (str.equals("kotlin.jvm.functions.Function13")) {
                            return "Function13";
                        }
                        return null;
                    case -1811142712:
                        if (str.equals("kotlin.jvm.functions.Function14")) {
                            return "Function14";
                        }
                        return null;
                    case -1811142711:
                        if (str.equals("kotlin.jvm.functions.Function15")) {
                            return "Function15";
                        }
                        return null;
                    case -1811142710:
                        if (str.equals("kotlin.jvm.functions.Function16")) {
                            return "Function16";
                        }
                        return null;
                    case -1811142709:
                        if (str.equals("kotlin.jvm.functions.Function17")) {
                            return "Function17";
                        }
                        return null;
                    case -1811142708:
                        if (str.equals("kotlin.jvm.functions.Function18")) {
                            return "Function18";
                        }
                        return null;
                    case -1811142707:
                        if (str.equals("kotlin.jvm.functions.Function19")) {
                            return "Function19";
                        }
                        return null;
                    default:
                        switch (hashCode) {
                            case -1811142685:
                                if (str.equals("kotlin.jvm.functions.Function20")) {
                                    return "Function20";
                                }
                                return null;
                            case -1811142684:
                                if (str.equals("kotlin.jvm.functions.Function21")) {
                                    return "Function21";
                                }
                                return null;
                            case -1811142683:
                                if (str.equals("kotlin.jvm.functions.Function22")) {
                                    return "Function22";
                                }
                                return null;
                            default:
                                switch (hashCode) {
                                    case 80123371:
                                        if (str.equals("kotlin.jvm.functions.Function0")) {
                                            return "Function0";
                                        }
                                        return null;
                                    case 80123372:
                                        if (str.equals("kotlin.jvm.functions.Function1")) {
                                            return "Function1";
                                        }
                                        return null;
                                    case 80123373:
                                        if (str.equals("kotlin.jvm.functions.Function2")) {
                                            return "Function2";
                                        }
                                        return null;
                                    case 80123374:
                                        if (str.equals("kotlin.jvm.functions.Function3")) {
                                            return "Function3";
                                        }
                                        return null;
                                    case 80123375:
                                        if (str.equals("kotlin.jvm.functions.Function4")) {
                                            return "Function4";
                                        }
                                        return null;
                                    case 80123376:
                                        if (str.equals("kotlin.jvm.functions.Function5")) {
                                            return "Function5";
                                        }
                                        return null;
                                    case 80123377:
                                        if (str.equals("kotlin.jvm.functions.Function6")) {
                                            return "Function6";
                                        }
                                        return null;
                                    case 80123378:
                                        if (str.equals("kotlin.jvm.functions.Function7")) {
                                            return "Function7";
                                        }
                                        return null;
                                    case 80123379:
                                        if (str.equals("kotlin.jvm.functions.Function8")) {
                                            return "Function8";
                                        }
                                        return null;
                                    case 80123380:
                                        if (str.equals("kotlin.jvm.functions.Function9")) {
                                            return "Function9";
                                        }
                                        return null;
                                    default:
                                        return null;
                                }
                        }
                }
        }
    }

    public static final String S(ij ijVar) {
        Object jq0Var;
        if (ijVar instanceof in) {
            return ((in) ijVar).toString();
        }
        try {
            jq0Var = ijVar + '@' + v(ijVar);
        } catch (Throwable th) {
            jq0Var = new jq0(th);
        }
        if (kq0.a(jq0Var) != null) {
            jq0Var = ijVar.getClass().getName() + '@' + v(ijVar);
        }
        return (String) jq0Var;
    }

    /* JADX WARN: Finally extract failed */
    public static boolean T(ByteArrayOutputStream byteArrayOutputStream, byte[] bArr, an[] anVarArr) {
        int i2;
        long j2;
        int length;
        byte[] bArr2 = n20.k;
        byte[] bArr3 = n20.j;
        byte[] bArr4 = n20.g;
        int i3 = 0;
        if (Arrays.equals(bArr, bArr4)) {
            ArrayList arrayList = new ArrayList(3);
            ArrayList arrayList2 = new ArrayList(3);
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            try {
                n20.Q(byteArrayOutputStream2, anVarArr.length);
                int i4 = 2;
                int i5 = 2;
                for (an anVar : anVarArr) {
                    n20.P(byteArrayOutputStream2, anVar.c, 4);
                    n20.P(byteArrayOutputStream2, anVar.d, 4);
                    n20.P(byteArrayOutputStream2, anVar.g, 4);
                    String t2 = t(anVar.a, anVar.b, bArr4);
                    Charset charset = StandardCharsets.UTF_8;
                    int length2 = t2.getBytes(charset).length;
                    n20.Q(byteArrayOutputStream2, length2);
                    i5 = i5 + 14 + length2;
                    byteArrayOutputStream2.write(t2.getBytes(charset));
                }
                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                if (i5 == byteArray.length) {
                    i81 i81Var = new i81(1, byteArray, false);
                    byteArrayOutputStream2.close();
                    arrayList.add(i81Var);
                    ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                    int i6 = 0;
                    int i7 = 0;
                    while (i6 < anVarArr.length) {
                        try {
                            an anVar2 = anVarArr[i6];
                            n20.Q(byteArrayOutputStream3, i6);
                            n20.Q(byteArrayOutputStream3, anVar2.e);
                            i7 = i7 + 4 + (anVar2.e * i4);
                            int[] iArr = anVar2.h;
                            int length3 = iArr.length;
                            int i8 = i3;
                            while (i3 < length3) {
                                int i9 = iArr[i3];
                                n20.Q(byteArrayOutputStream3, i9 - i8);
                                i3++;
                                i4 = i4;
                                i8 = i9;
                            }
                            i6++;
                            i3 = 0;
                        } catch (Throwable th) {
                        }
                    }
                    int i10 = i4;
                    byte[] byteArray2 = byteArrayOutputStream3.toByteArray();
                    if (i7 == byteArray2.length) {
                        i81 i81Var2 = new i81(3, byteArray2, true);
                        byteArrayOutputStream3.close();
                        arrayList.add(i81Var2);
                        byteArrayOutputStream3 = new ByteArrayOutputStream();
                        int i11 = 0;
                        for (int i12 = 0; i12 < anVarArr.length; i12++) {
                            try {
                                an anVar3 = anVarArr[i12];
                                Iterator it = anVar3.i.entrySet().iterator();
                                int i13 = 0;
                                while (it.hasNext()) {
                                    i13 |= ((Integer) ((Map.Entry) it.next()).getValue()).intValue();
                                }
                                ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                                try {
                                    W(byteArrayOutputStream4, i13, anVar3);
                                    byte[] byteArray3 = byteArrayOutputStream4.toByteArray();
                                    byteArrayOutputStream4.close();
                                    byteArrayOutputStream4 = new ByteArrayOutputStream();
                                    try {
                                        X(byteArrayOutputStream4, anVar3);
                                        byte[] byteArray4 = byteArrayOutputStream4.toByteArray();
                                        byteArrayOutputStream4.close();
                                        n20.Q(byteArrayOutputStream3, i12);
                                        int length4 = byteArray3.length + 2 + byteArray4.length;
                                        int i14 = i11 + 6;
                                        n20.P(byteArrayOutputStream3, length4, 4);
                                        n20.Q(byteArrayOutputStream3, i13);
                                        byteArrayOutputStream3.write(byteArray3);
                                        byteArrayOutputStream3.write(byteArray4);
                                        i11 = i14 + length4;
                                    } finally {
                                    }
                                } finally {
                                }
                            } finally {
                                try {
                                    byteArrayOutputStream3.close();
                                    throw th;
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                        }
                        byte[] byteArray5 = byteArrayOutputStream3.toByteArray();
                        if (i11 == byteArray5.length) {
                            i81 i81Var3 = new i81(4, byteArray5, true);
                            byteArrayOutputStream3.close();
                            arrayList.add(i81Var3);
                            long size = 12 + (arrayList.size() * 16);
                            n20.P(byteArrayOutputStream, arrayList.size(), 4);
                            int i15 = 0;
                            while (i15 < arrayList.size()) {
                                i81 i81Var4 = (i81) arrayList.get(i15);
                                int i16 = i81Var4.a;
                                byte[] bArr5 = i81Var4.b;
                                if (i16 != 1) {
                                    i2 = i10;
                                    if (i16 != i2) {
                                        if (i16 != 3) {
                                            if (i16 != 4) {
                                                if (i16 == 5) {
                                                    j2 = 4;
                                                } else {
                                                    throw null;
                                                }
                                            } else {
                                                j2 = 3;
                                            }
                                        } else {
                                            j2 = 2;
                                        }
                                    } else {
                                        j2 = 1;
                                    }
                                } else {
                                    i2 = i10;
                                    j2 = 0;
                                }
                                n20.P(byteArrayOutputStream, j2, 4);
                                n20.P(byteArrayOutputStream, size, 4);
                                if (i81Var4.c) {
                                    long length5 = bArr5.length;
                                    byte[] n2 = n20.n(bArr5);
                                    arrayList2.add(n2);
                                    n20.P(byteArrayOutputStream, n2.length, 4);
                                    n20.P(byteArrayOutputStream, length5, 4);
                                    length = n2.length;
                                } else {
                                    arrayList2.add(bArr5);
                                    n20.P(byteArrayOutputStream, bArr5.length, 4);
                                    n20.P(byteArrayOutputStream, 0L, 4);
                                    length = bArr5.length;
                                }
                                size += length;
                                i15++;
                                i10 = i2;
                            }
                            for (int i17 = 0; i17 < arrayList2.size(); i17++) {
                                byteArrayOutputStream.write((byte[]) arrayList2.get(i17));
                            }
                            return true;
                        }
                        throw new IllegalStateException("Expected size " + i11 + ", does not match actual size " + byteArray5.length);
                    }
                    throw new IllegalStateException("Expected size " + i7 + ", does not match actual size " + byteArray2.length);
                }
                throw new IllegalStateException("Expected size " + i5 + ", does not match actual size " + byteArray.length);
            } catch (Throwable th3) {
                try {
                    byteArrayOutputStream2.close();
                    throw th3;
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                    throw th3;
                }
            }
        }
        byte[] bArr6 = n20.h;
        if (Arrays.equals(bArr, bArr6)) {
            byte[] s2 = s(anVarArr, bArr6);
            n20.P(byteArrayOutputStream, anVarArr.length, 1);
            n20.P(byteArrayOutputStream, s2.length, 4);
            byte[] n3 = n20.n(s2);
            n20.P(byteArrayOutputStream, n3.length, 4);
            byteArrayOutputStream.write(n3);
            return true;
        }
        if (Arrays.equals(bArr, bArr3)) {
            n20.P(byteArrayOutputStream, anVarArr.length, 1);
            for (an anVar4 : anVarArr) {
                int size2 = anVar4.i.size() * 4;
                String t3 = t(anVar4.a, anVar4.b, bArr3);
                Charset charset2 = StandardCharsets.UTF_8;
                n20.Q(byteArrayOutputStream, t3.getBytes(charset2).length);
                n20.Q(byteArrayOutputStream, anVar4.h.length);
                n20.P(byteArrayOutputStream, size2, 4);
                n20.P(byteArrayOutputStream, anVar4.c, 4);
                byteArrayOutputStream.write(t3.getBytes(charset2));
                Iterator it2 = anVar4.i.keySet().iterator();
                while (it2.hasNext()) {
                    n20.Q(byteArrayOutputStream, ((Integer) it2.next()).intValue());
                    n20.Q(byteArrayOutputStream, 0);
                }
                for (int i18 : anVar4.h) {
                    n20.Q(byteArrayOutputStream, i18);
                }
            }
            return true;
        }
        byte[] bArr7 = n20.i;
        if (Arrays.equals(bArr, bArr7)) {
            byte[] s3 = s(anVarArr, bArr7);
            n20.P(byteArrayOutputStream, anVarArr.length, 1);
            n20.P(byteArrayOutputStream, s3.length, 4);
            byte[] n4 = n20.n(s3);
            n20.P(byteArrayOutputStream, n4.length, 4);
            byteArrayOutputStream.write(n4);
            return true;
        }
        if (Arrays.equals(bArr, bArr2)) {
            n20.Q(byteArrayOutputStream, anVarArr.length);
            for (an anVar5 : anVarArr) {
                String str = anVar5.a;
                TreeMap treeMap = anVar5.i;
                String t4 = t(str, anVar5.b, bArr2);
                Charset charset3 = StandardCharsets.UTF_8;
                n20.Q(byteArrayOutputStream, t4.getBytes(charset3).length);
                n20.Q(byteArrayOutputStream, treeMap.size());
                n20.Q(byteArrayOutputStream, anVar5.h.length);
                n20.P(byteArrayOutputStream, anVar5.c, 4);
                byteArrayOutputStream.write(t4.getBytes(charset3));
                Iterator it3 = treeMap.keySet().iterator();
                while (it3.hasNext()) {
                    n20.Q(byteArrayOutputStream, ((Integer) it3.next()).intValue());
                }
                for (int i19 : anVar5.h) {
                    n20.Q(byteArrayOutputStream, i19);
                }
            }
            return true;
        }
        return false;
    }

    public static void U(ByteArrayOutputStream byteArrayOutputStream, an anVar) {
        X(byteArrayOutputStream, anVar);
        int i2 = anVar.g;
        int[] iArr = anVar.h;
        int length = iArr.length;
        int i3 = 0;
        int i4 = 0;
        while (i3 < length) {
            int i5 = iArr[i3];
            n20.Q(byteArrayOutputStream, i5 - i4);
            i3++;
            i4 = i5;
        }
        byte[] bArr = new byte[(((i2 * 2) + 7) & (-8)) / 8];
        for (Map.Entry entry : anVar.i.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            int intValue2 = ((Integer) entry.getValue()).intValue();
            if ((intValue2 & 2) != 0) {
                int i6 = intValue / 8;
                bArr[i6] = (byte) (bArr[i6] | (1 << (intValue % 8)));
            }
            if ((intValue2 & 4) != 0) {
                int i7 = intValue + i2;
                int i8 = i7 / 8;
                bArr[i8] = (byte) ((1 << (i7 % 8)) | bArr[i8]);
            }
        }
        byteArrayOutputStream.write(bArr);
    }

    public static void V(ByteArrayOutputStream byteArrayOutputStream, an anVar, String str) {
        Charset charset = StandardCharsets.UTF_8;
        n20.Q(byteArrayOutputStream, str.getBytes(charset).length);
        n20.Q(byteArrayOutputStream, anVar.e);
        n20.P(byteArrayOutputStream, anVar.f, 4);
        n20.P(byteArrayOutputStream, anVar.c, 4);
        n20.P(byteArrayOutputStream, anVar.g, 4);
        byteArrayOutputStream.write(str.getBytes(charset));
    }

    public static void W(ByteArrayOutputStream byteArrayOutputStream, int i2, an anVar) {
        int i3 = anVar.g;
        byte[] bArr = new byte[(((Integer.bitCount(i2 & (-2)) * i3) + 7) & (-8)) / 8];
        for (Map.Entry entry : anVar.i.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            int intValue2 = ((Integer) entry.getValue()).intValue();
            int i4 = 0;
            for (int i5 = 1; i5 <= 4; i5 <<= 1) {
                if (i5 != 1 && (i5 & i2) != 0) {
                    if ((i5 & intValue2) == i5) {
                        int i6 = (i4 * i3) + intValue;
                        int i7 = i6 / 8;
                        bArr[i7] = (byte) ((1 << (i6 % 8)) | bArr[i7]);
                    }
                    i4++;
                }
            }
        }
        byteArrayOutputStream.write(bArr);
    }

    public static void X(ByteArrayOutputStream byteArrayOutputStream, an anVar) {
        int i2 = 0;
        for (Map.Entry entry : anVar.i.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            if ((((Integer) entry.getValue()).intValue() & 1) != 0) {
                n20.Q(byteArrayOutputStream, intValue - i2);
                n20.Q(byteArrayOutputStream, 0);
                i2 = intValue;
            }
        }
    }

    public static final y6 a(float f2, float f3) {
        return new y6(Float.valueOf(f2), t, Float.valueOf(f3), 8);
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void b(final java.lang.String r17, defpackage.cd0 r18, defpackage.r11 r19, int r20, boolean r21, int r22, int r23, defpackage.u2 r24, defpackage.bw r25, final int r26, final int r27) {
        /*
            Method dump skipped, instructions count: 552
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dl.b(java.lang.String, cd0, r11, int, boolean, int, int, u2, bw, int, int):void");
    }

    public static final void c(bw bwVar, int i2) {
        boolean z;
        long j2;
        bwVar.W(-1152830576);
        boolean z2 = false;
        if (i2 != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            if (!n20.D(bwVar)) {
                j2 = se.b;
            } else {
                j2 = se.c;
            }
            f31.b(null, jc0.C(255672613, new pa(n30.E(f31.C(), bwVar), new da(5, j2), j2), bwVar), bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new yu0(i2, 4, z2);
        }
    }

    public static final hj d(yj yjVar) {
        if (yjVar.j(x1.L) == null) {
            yjVar = yjVar.i(new f30(null));
        }
        return new hj(yjVar);
    }

    public static pm e() {
        return new pm(1.0f, 1.0f);
    }

    public static final void f(Object obj, gv gvVar, bw bwVar) {
        boolean f2 = bwVar.f(obj);
        Object L = bwVar.L();
        if (f2 || L == ph.a) {
            L = new rn(gvVar);
            bwVar.f0(L);
        }
    }

    public static final void g(Object obj, Object obj2, gv gvVar, bw bwVar) {
        boolean f2 = bwVar.f(obj) | bwVar.f(obj2);
        Object L = bwVar.L();
        if (f2 || L == ph.a) {
            L = new rn(gvVar);
            bwVar.f0(L);
        }
    }

    public static final void h(bw bwVar, int i2) {
        boolean z;
        bwVar.W(503237982);
        boolean z2 = false;
        if (i2 != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            Object L = bwVar.L();
            dt0 dt0Var = ph.a;
            if (L == dt0Var) {
                L = r(bwVar);
                bwVar.f0(L);
            }
            hk hkVar = (hk) L;
            Object L2 = bwVar.L();
            if (L2 == dt0Var) {
                L2 = new y6(new ch0(0L), u, null, 12);
                bwVar.f0(L2);
            }
            y6 y6Var = (y6) L2;
            Object L3 = bwVar.L();
            if (L3 == dt0Var) {
                L3 = a(1.0f, 0.01f);
                bwVar.f0(L3);
            }
            y6 y6Var2 = (y6) L3;
            Object L4 = bwVar.L();
            if (L4 == dt0Var) {
                L4 = a(0.0f, 0.01f);
                bwVar.f0(L4);
            }
            y6 y6Var3 = (y6) L4;
            Object L5 = bwVar.L();
            if (L5 == dt0Var) {
                L5 = n30.B(Boolean.TRUE);
                bwVar.f0(L5);
            }
            af0 af0Var = (af0) L5;
            Object L6 = bwVar.L();
            if (L6 == dt0Var) {
                L6 = new ek0(0.5f);
                bwVar.f0(L6);
            }
            ek0 ek0Var = (ek0) L6;
            Object L7 = bwVar.L();
            if (L7 == dt0Var) {
                L7 = new ek0(0.0f);
                bwVar.f0(L7);
            }
            ek0 ek0Var2 = (ek0) L7;
            Object L8 = bwVar.L();
            if (L8 == dt0Var) {
                L8 = new ek0(0.2f);
                bwVar.f0(L8);
            }
            ek0 ek0Var3 = (ek0) L8;
            Object L9 = bwVar.L();
            if (L9 == dt0Var) {
                L9 = new ek0(0.2f);
                bwVar.f0(L9);
            }
            ek0 ek0Var4 = (ek0) L9;
            Object L10 = bwVar.L();
            if (L10 == dt0Var) {
                L10 = new ek0(0.0f);
                bwVar.f0(L10);
            }
            f31.b(null, jc0.C(1461888179, new vw(y6Var, y6Var2, y6Var3, hkVar, ek0Var, ek0Var2, ek0Var3, ek0Var4, (ek0) L10, af0Var), bwVar), bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new yu0(i2, 8, z2);
        }
    }

    public static final void i(kv kvVar, bw bwVar, Object obj) {
        yj yjVar = bwVar.R;
        boolean f2 = bwVar.f(obj);
        Object L = bwVar.L();
        if (f2 || L == ph.a) {
            L = new b40(yjVar, kvVar);
            bwVar.f0(L);
        }
    }

    public static final void j(vu vuVar, bw bwVar) {
        bj0 bj0Var = bwVar.M.b.w;
        bj0Var.G(qi0.c);
        t20.O(bj0Var, 0, vuVar);
    }

    public static final boolean k(r11 r11Var) {
        yq yqVar;
        mm0 mm0Var;
        nm0 nm0Var = r11Var.c;
        if (nm0Var != null && (mm0Var = nm0Var.a) != null) {
            yqVar = new yq(mm0Var.b);
        } else {
            yqVar = null;
        }
        boolean z = false;
        if (yqVar != null && yqVar.a == 1) {
            z = true;
        }
        return !z;
    }

    public static final double l(double d2, double d3, double d4, double d5) {
        double d6 = d2 * d2;
        double d7 = (((d4 * 3.0d) / d2) - ((d3 * d3) / d6)) / 3.0d;
        double d8 = (((d5 * 27.0d) / d2) + (((((d3 * 2.0d) * d3) * d3) / (d6 * d2)) - (((9.0d * d3) * d4) / d6))) / 27.0d;
        double sqrt = Math.sqrt((((d7 * d7) * d7) / 27.0d) + ((d8 * d8) / 4.0d));
        double d9 = (-d8) / 2.0d;
        return (Math.cbrt(d9 - sqrt) + Math.cbrt(d9 + sqrt)) - (d3 / (d2 * 3.0d));
    }

    public static final void m(k1 k1Var, su0 su0Var) {
        nu0 nu0Var = su0Var.d;
        ve0 ve0Var = nu0Var.e;
        Object g2 = nu0Var.e.g(wu0.w);
        if (g2 == null) {
            g2 = null;
        }
        cr0 cr0Var = (cr0) g2;
        if (n20.f(su0Var)) {
            if (cr0Var == null || cr0Var.a != 8) {
                Object g3 = ve0Var.g(mu0.x);
                if (g3 == null) {
                    g3 = null;
                }
                n0 n0Var = (n0) g3;
                if (n0Var != null) {
                    k1Var.a(new e1(null, R.id.accessibilityActionPageUp, n0Var.a, null));
                }
                Object g4 = ve0Var.g(mu0.z);
                if (g4 == null) {
                    g4 = null;
                }
                n0 n0Var2 = (n0) g4;
                if (n0Var2 != null) {
                    k1Var.a(new e1(null, R.id.accessibilityActionPageDown, n0Var2.a, null));
                }
                Object g5 = ve0Var.g(mu0.y);
                if (g5 == null) {
                    g5 = null;
                }
                n0 n0Var3 = (n0) g5;
                if (n0Var3 != null) {
                    k1Var.a(new e1(null, R.id.accessibilityActionPageLeft, n0Var3.a, null));
                }
                Object g6 = ve0Var.g(mu0.A);
                if (g6 == null) {
                    g6 = null;
                }
                n0 n0Var4 = (n0) g6;
                if (n0Var4 != null) {
                    k1Var.a(new e1(null, R.id.accessibilityActionPageRight, n0Var4.a, null));
                }
            }
        }
    }

    public static String n(String str) {
        int hashCode = str.hashCode();
        switch (hashCode) {
            case -2061550653:
                if (!str.equals("kotlin.jvm.internal.DoubleCompanionObject")) {
                    return null;
                }
                return "kotlin.Double.Companion";
            case -2056817302:
                if (str.equals("java.lang.Integer")) {
                    return "kotlin.Int";
                }
                return null;
            case -2034166429:
                if (str.equals("java.lang.Cloneable")) {
                    return "kotlin.Cloneable";
                }
                return null;
            case -1979556166:
                if (str.equals("java.lang.annotation.Annotation")) {
                    return "kotlin.Annotation";
                }
                return null;
            case -1571515090:
                if (str.equals("java.lang.Comparable")) {
                    return "kotlin.Comparable";
                }
                return null;
            case -1383349348:
                if (str.equals("java.util.Map")) {
                    return "kotlin.collections.Map";
                }
                return null;
            case -1383343454:
                if (str.equals("java.util.Set")) {
                    return "kotlin.collections.Set";
                }
                return null;
            case -1325958191:
                if (str.equals("double")) {
                    return "kotlin.Double";
                }
                return null;
            case -1182275604:
                if (str.equals("kotlin.jvm.internal.ByteCompanionObject")) {
                    return "kotlin.Byte.Companion";
                }
                return null;
            case -1062240117:
                if (str.equals("java.lang.CharSequence")) {
                    return "kotlin.CharSequence";
                }
                return null;
            case -688322466:
                if (str.equals("java.util.Collection")) {
                    return "kotlin.collections.Collection";
                }
                return null;
            case -527879800:
                if (str.equals("java.lang.Float")) {
                    return "kotlin.Float";
                }
                return null;
            case -515992664:
                if (str.equals("java.lang.Short")) {
                    return "kotlin.Short";
                }
                return null;
            case -246476834:
                if (str.equals("kotlin.jvm.internal.CharCompanionObject")) {
                    return "kotlin.Char.Companion";
                }
                return null;
            case -207262728:
                if (str.equals("kotlin.jvm.internal.LongCompanionObject")) {
                    return "kotlin.Long.Companion";
                }
                return null;
            case -165139126:
                if (str.equals("java.util.Map$Entry")) {
                    return "kotlin.collections.Map.Entry";
                }
                return null;
            case 104431:
                if (str.equals("int")) {
                    return "kotlin.Int";
                }
                return null;
            case 3039496:
                if (str.equals("byte")) {
                    return "kotlin.Byte";
                }
                return null;
            case 3052374:
                if (str.equals("char")) {
                    return "kotlin.Char";
                }
                return null;
            case 3327612:
                if (str.equals("long")) {
                    return "kotlin.Long";
                }
                return null;
            case 64711720:
                if (str.equals("boolean")) {
                    return "kotlin.Boolean";
                }
                return null;
            case 65821278:
                if (str.equals("java.util.List")) {
                    return "kotlin.collections.List";
                }
                return null;
            case 77230534:
                if (str.equals("kotlin.jvm.internal.ShortCompanionObject")) {
                    return "kotlin.Short.Companion";
                }
                return null;
            case 97526364:
                if (str.equals("float")) {
                    return "kotlin.Float";
                }
                return null;
            case 109413500:
                if (str.equals("short")) {
                    return "kotlin.Short";
                }
                return null;
            case 155276373:
                if (str.equals("java.lang.Character")) {
                    return "kotlin.Char";
                }
                return null;
            case 226173651:
                if (str.equals("kotlin.jvm.internal.EnumCompanionObject")) {
                    return "kotlin.Enum.Companion";
                }
                return null;
            case 344809556:
                if (str.equals("java.lang.Boolean")) {
                    return "kotlin.Boolean";
                }
                return null;
            case 398507100:
                if (str.equals("java.lang.Byte")) {
                    return "kotlin.Byte";
                }
                return null;
            case 398585941:
                if (str.equals("java.lang.Enum")) {
                    return "kotlin.Enum";
                }
                return null;
            case 398795216:
                if (str.equals("java.lang.Long")) {
                    return "kotlin.Long";
                }
                return null;
            case 482629606:
                if (str.equals("kotlin.jvm.internal.FloatCompanionObject")) {
                    return "kotlin.Float.Companion";
                }
                return null;
            case 499831342:
                if (str.equals("java.util.Iterator")) {
                    return "kotlin.collections.Iterator";
                }
                return null;
            case 577341676:
                if (str.equals("java.util.ListIterator")) {
                    return "kotlin.collections.ListIterator";
                }
                return null;
            case 599019395:
                if (str.equals("kotlin.jvm.internal.StringCompanionObject")) {
                    return "kotlin.String.Companion";
                }
                return null;
            case 761287205:
                if (str.equals("java.lang.Double")) {
                    return "kotlin.Double";
                }
                return null;
            case 1052881309:
                if (str.equals("java.lang.Number")) {
                    return "kotlin.Number";
                }
                return null;
            case 1063877011:
                if (str.equals("java.lang.Object")) {
                    return "kotlin.Any";
                }
                return null;
            case 1195259493:
                if (str.equals("java.lang.String")) {
                    return "kotlin.String";
                }
                return null;
            case 1275614662:
                if (str.equals("java.lang.Iterable")) {
                    return "kotlin.collections.Iterable";
                }
                return null;
            case 1383693018:
                if (str.equals("kotlin.jvm.internal.BooleanCompanionObject")) {
                    return "kotlin.Boolean.Companion";
                }
                return null;
            case 1630335596:
                if (str.equals("java.lang.Throwable")) {
                    return "kotlin.Throwable";
                }
                return null;
            case 1877171123:
                if (str.equals("kotlin.jvm.internal.IntCompanionObject")) {
                    return "kotlin.Int.Companion";
                }
                return null;
            default:
                switch (hashCode) {
                    case -1811142716:
                        if (str.equals("kotlin.jvm.functions.Function10")) {
                            return "kotlin.Function10";
                        }
                        return null;
                    case -1811142715:
                        if (str.equals("kotlin.jvm.functions.Function11")) {
                            return "kotlin.Function11";
                        }
                        return null;
                    case -1811142714:
                        if (str.equals("kotlin.jvm.functions.Function12")) {
                            return "kotlin.Function12";
                        }
                        return null;
                    case -1811142713:
                        if (str.equals("kotlin.jvm.functions.Function13")) {
                            return "kotlin.Function13";
                        }
                        return null;
                    case -1811142712:
                        if (str.equals("kotlin.jvm.functions.Function14")) {
                            return "kotlin.Function14";
                        }
                        return null;
                    case -1811142711:
                        if (str.equals("kotlin.jvm.functions.Function15")) {
                            return "kotlin.Function15";
                        }
                        return null;
                    case -1811142710:
                        if (str.equals("kotlin.jvm.functions.Function16")) {
                            return "kotlin.Function16";
                        }
                        return null;
                    case -1811142709:
                        if (str.equals("kotlin.jvm.functions.Function17")) {
                            return "kotlin.Function17";
                        }
                        return null;
                    case -1811142708:
                        if (str.equals("kotlin.jvm.functions.Function18")) {
                            return "kotlin.Function18";
                        }
                        return null;
                    case -1811142707:
                        if (str.equals("kotlin.jvm.functions.Function19")) {
                            return "kotlin.Function19";
                        }
                        return null;
                    default:
                        switch (hashCode) {
                            case -1811142685:
                                if (str.equals("kotlin.jvm.functions.Function20")) {
                                    return "kotlin.Function20";
                                }
                                return null;
                            case -1811142684:
                                if (str.equals("kotlin.jvm.functions.Function21")) {
                                    return "kotlin.Function21";
                                }
                                return null;
                            case -1811142683:
                                if (str.equals("kotlin.jvm.functions.Function22")) {
                                    return "kotlin.Function22";
                                }
                                return null;
                            default:
                                switch (hashCode) {
                                    case 80123371:
                                        if (str.equals("kotlin.jvm.functions.Function0")) {
                                            return "kotlin.Function0";
                                        }
                                        return null;
                                    case 80123372:
                                        if (str.equals("kotlin.jvm.functions.Function1")) {
                                            return "kotlin.Function1";
                                        }
                                        return null;
                                    case 80123373:
                                        if (str.equals("kotlin.jvm.functions.Function2")) {
                                            return "kotlin.Function2";
                                        }
                                        return null;
                                    case 80123374:
                                        if (str.equals("kotlin.jvm.functions.Function3")) {
                                            return "kotlin.Function3";
                                        }
                                        return null;
                                    case 80123375:
                                        if (str.equals("kotlin.jvm.functions.Function4")) {
                                            return "kotlin.Function4";
                                        }
                                        return null;
                                    case 80123376:
                                        if (str.equals("kotlin.jvm.functions.Function5")) {
                                            return "kotlin.Function5";
                                        }
                                        return null;
                                    case 80123377:
                                        if (str.equals("kotlin.jvm.functions.Function6")) {
                                            return "kotlin.Function6";
                                        }
                                        return null;
                                    case 80123378:
                                        if (str.equals("kotlin.jvm.functions.Function7")) {
                                            return "kotlin.Function7";
                                        }
                                        return null;
                                    case 80123379:
                                        if (str.equals("kotlin.jvm.functions.Function8")) {
                                            return "kotlin.Function8";
                                        }
                                        return null;
                                    case 80123380:
                                        if (str.equals("kotlin.jvm.functions.Function9")) {
                                            return "kotlin.Function9";
                                        }
                                        return null;
                                    default:
                                        return null;
                                }
                        }
                }
        }
    }

    public static final boolean o(pt ptVar, boolean z) {
        boolean z2;
        int ordinal = ptVar.I0().ordinal();
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal != 3) {
                        v7.k();
                        return false;
                    }
                } else {
                    return z;
                }
            } else {
                pt v = n20.v(ptVar);
                if (v != null) {
                    z2 = o(v, z);
                } else {
                    z2 = true;
                }
                if (!z2) {
                    return false;
                }
                ptVar.E0(ot.f, ot.g);
                return true;
            }
        }
        return true;
    }

    public static final i7 p(i7 i7Var) {
        i7 c2 = i7Var.c();
        int b2 = c2.b();
        for (int i2 = 0; i2 < b2; i2++) {
            c2.e(i7Var.a(i2), i2);
        }
        return c2;
    }

    public static final Object q(kv kvVar, ij ijVar) {
        ct0 ct0Var = new ct0(ijVar, ijVar.r());
        return o30.w(ct0Var, ct0Var, kvVar);
    }

    public static final hk r(bw bwVar) {
        return new qp0(bwVar.R);
    }

    public static byte[] s(an[] anVarArr, byte[] bArr) {
        int i2 = 0;
        int i3 = 0;
        for (an anVar : anVarArr) {
            i3 += ((((anVar.g * 2) + 7) & (-8)) / 8) + (anVar.e * 2) + t(anVar.a, anVar.b, bArr).getBytes(StandardCharsets.UTF_8).length + 16 + anVar.f;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i3);
        if (Arrays.equals(bArr, n20.i)) {
            int length = anVarArr.length;
            while (i2 < length) {
                an anVar2 = anVarArr[i2];
                V(byteArrayOutputStream, anVar2, t(anVar2.a, anVar2.b, bArr));
                U(byteArrayOutputStream, anVar2);
                i2++;
            }
        } else {
            for (an anVar3 : anVarArr) {
                V(byteArrayOutputStream, anVar3, t(anVar3.a, anVar3.b, bArr));
            }
            int length2 = anVarArr.length;
            while (i2 < length2) {
                U(byteArrayOutputStream, anVarArr[i2]);
                i2++;
            }
        }
        if (byteArrayOutputStream.size() == i3) {
            return byteArrayOutputStream.toByteArray();
        }
        throw new IllegalStateException("The bytes saved do not match expectation. actual=" + byteArrayOutputStream.size() + " expected=" + i3);
    }

    public static String t(String str, String str2, byte[] bArr) {
        Object obj;
        byte[] bArr2 = n20.j;
        byte[] bArr3 = n20.k;
        String str3 = "!";
        if (!Arrays.equals(bArr, bArr3) && !Arrays.equals(bArr, bArr2)) {
            obj = "!";
        } else {
            obj = ":";
        }
        if (str.length() <= 0) {
            if ("!".equals(obj)) {
                return str2.replace(":", "!");
            }
            if (":".equals(obj)) {
                return str2.replace("!", ":");
            }
        } else {
            if (str2.equals("classes.dex")) {
                return str;
            }
            if (!str2.contains("!") && !str2.contains(":")) {
                if (!str2.endsWith(".apk")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    if (Arrays.equals(bArr, bArr3) || Arrays.equals(bArr, bArr2)) {
                        str3 = ":";
                    }
                    sb.append(str3);
                    sb.append(str2);
                    return sb.toString();
                }
            } else {
                if ("!".equals(obj)) {
                    return str2.replace(":", "!");
                }
                if (":".equals(obj)) {
                    return str2.replace("!", ":");
                }
            }
        }
        return str2;
    }

    public static Set u() {
        try {
            Object invoke = Class.forName("android.text.EmojiConsistency").getMethod("getEmojiConsistencySet", null).invoke(null, null);
            if (invoke == null) {
                return Collections.EMPTY_SET;
            }
            Set set = (Set) invoke;
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (!(it.next() instanceof int[])) {
                    return Collections.EMPTY_SET;
                }
            }
            return set;
        } catch (Throwable unused) {
            return Collections.EMPTY_SET;
        }
    }

    public static final String v(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    public static final pc w(ij ijVar) {
        if (!(ijVar instanceof in)) {
            return new pc(1, ijVar);
        }
        pc j2 = ((in) ijVar).j();
        if (j2 != null) {
            if (!j2.E()) {
                j2 = null;
            }
            if (j2 != null) {
                return j2;
            }
        }
        return new pc(2, ijVar);
    }

    public static final long x(j2 j2Var) {
        DragEvent dragEvent = (DragEvent) j2Var.f;
        float x = dragEvent.getX();
        float y = dragEvent.getY();
        return (Float.floatToRawIntBits(x) << 32) | (Float.floatToRawIntBits(y) & 4294967295L);
    }

    public static final boolean y(hk hkVar) {
        d30 d30Var = (d30) hkVar.g().j(x1.L);
        if (d30Var != null) {
            return d30Var.b();
        }
        return true;
    }

    public static final cd0 z(y60 y60Var, ib ibVar, dj0 dj0Var) {
        return new v50(y60Var, ibVar, dj0Var);
    }
}
