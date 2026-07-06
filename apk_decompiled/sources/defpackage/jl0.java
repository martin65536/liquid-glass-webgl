package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class jl0 {
    public static final jl0 e;
    public static final jl0 f;
    public static final jl0 g;
    public static final jl0 h;
    public static final jl0 i;
    public static final jl0 j;
    public static final jl0 k;
    public static final /* synthetic */ jl0[] l;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, jl0] */
    /* JADX WARN: Type inference failed for: r11v1, types: [java.lang.Enum, jl0] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, jl0] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, jl0] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.Enum, jl0] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.lang.Enum, jl0] */
    /* JADX WARN: Type inference failed for: r9v1, types: [java.lang.Enum, jl0] */
    static {
        ?? r0 = new Enum("Invalid", 0);
        e = r0;
        ?? r1 = new Enum("Cancelled", 1);
        f = r1;
        ?? r3 = new Enum("InitialPending", 2);
        g = r3;
        ?? r5 = new Enum("RecomposePending", 3);
        h = r5;
        ?? r7 = new Enum("Recomposing", 4);
        i = r7;
        ?? r9 = new Enum("ApplyPending", 5);
        j = r9;
        ?? r11 = new Enum("Applied", 6);
        k = r11;
        l = new jl0[]{r0, r1, r3, r5, r7, r9, r11};
    }

    public static jl0 valueOf(String str) {
        return (jl0) Enum.valueOf(jl0.class, str);
    }

    public static jl0[] values() {
        return (jl0[]) l.clone();
    }
}
