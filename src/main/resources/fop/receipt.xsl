<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="first"
					margin-right="0.5cm" margin-left="0.5cm" margin-bottom="0.5cm"
					margin-top="1.0cm" page-width="21cm" page-height="29.7cm">
					<fo:region-body></fo:region-body>
				</fo:simple-page-master>

				<fo:page-sequence-master master-name="psmA">
					<fo:repeatable-page-master-alternatives>
						<fo:conditional-page-master-reference
							master-reference="first" page-position="first" />
					</fo:repeatable-page-master-alternatives>
				</fo:page-sequence-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="psmA">

				<fo:flow flow-name="xsl-region-body">
					<fo:block-container height="4cm" width="12cm"
						top="0cm" left="0cm" position="absolute">
						<fo:block>
							<fo:external-graphic src="./images/logo.jpg" />
						</fo:block>
						<fo:block text-align="start" line-height="18pt"
							font-family="sans-serif" font-size="13pt">
							KA Family Day Care
						</fo:block>
						<fo:block text-align="start" line-height="18pt"
							font-family="sans-serif" font-size="13pt">
							26 Toorak Place
						</fo:block>
						<fo:block text-align="start" line-height="18pt"
							font-family="sans-serif" font-size="13pt">
							Runcorn QLD 4113
						</fo:block>
						<fo:block text-align="start" line-height="18pt"
							font-family="sans-serif" font-size="13pt">
							Ph: (07) 3108 0348
						</fo:block>
						<fo:block text-align="start" line-height="18pt"
							font-family="sans-serif" font-size="13pt">
							Email: kafdc.qad@gmail.com
						</fo:block>
					</fo:block-container>
					<fo:block-container height="1cm" width="6cm"
						top="0cm" left="16cm" position="absolute">
						<fo:block text-align="start" space-after.optimum="3pt">
							<fo:external-graphic src="./images/ka_sig.png" />
						</fo:block>
					</fo:block-container>
					<fo:block-container height="1.5cm" width="5cm"
						top="3.2cm" left="8.8cm" padding=".6mm" position="absolute">
						<fo:block text-align="start" space-after.optimum="3pt"
							line-height="14pt" font-family="sans-serif" font-weight="bold"
							font-size="18pt">
							RECEIPT
						</fo:block>
					</fo:block-container>
					<fo:block-container border-color="black"
						border-style="solid" border-width=".5mm" height="0.5cm" width="2.49cm"
						top="3.2cm" left="13.8cm" padding=".6mm" position="absolute">
						<fo:block text-align="start" space-after.optimum="3pt"
							line-height="14pt" font-family="sans-serif" font-size="12pt">
							DATE
						</fo:block>
					</fo:block-container>
					<fo:block-container border-color="black"
						border-style="solid" border-width=".5mm" height="0.5cm" width="2.49cm"
						top="3.87cm" left="13.8cm" padding=".6mm" position="absolute">
						<fo:block text-align="start" space-after.optimum="3pt"
							line-height="14pt" font-family="sans-serif" font-size="10pt">
							<xsl:value-of select="root/date" />
						</fo:block>
					</fo:block-container>
					<fo:block-container border-color="black"
						border-style="solid" border-width=".5mm" height="0.5cm" width="2.49cm"
						top="3.2cm" left="16.46cm" padding=".6mm" position="absolute">
						<fo:block text-align="start" space-after.optimum="3pt"
							line-height="14pt" font-family="sans-serif" font-size="12pt">
							RECEIPT #
						</fo:block>
					</fo:block-container>
					<fo:block-container border-color="black"
						border-style="solid" border-width=".5mm" height="0.5cm" width="2.49cm"
						top="3.87cm" left="16.46cm" padding=".6mm" position="absolute">
						<fo:block text-align="start" space-after.optimum="3pt"
							line-height="14pt" font-family="sans-serif" font-size="10pt">
							<xsl:value-of select="root/receiptNumber" />
						</fo:block>
					</fo:block-container>
					<fo:block-container height="14cm" width="12cm"
						top="6cm" left="6cm" position="absolute">
						<fo:block text-align="right" line-height="18pt"
							font-family="sans-serif" font-size="11pt">
							Aspley Family Day Care
						</fo:block>
						<fo:block text-align="right" line-height="18pt"
							font-family="sans-serif" font-size="11pt">
							66 PIE ST ASPLEY QLD 4034
						</fo:block>
						<fo:block text-align="right" line-height="18pt"
							font-family="sans-serif" font-size="11pt">
							Ph: 04-2562-9524
						</fo:block>
						<fo:block text-align="right" line-height="18pt"
							font-family="sans-serif" font-size="11pt">
							Email:
							aspleyfamilydaycare@gmail.com
						</fo:block>
						<fo:block text-align="right" line-height="18pt"
							font-family="sans-serif" font-size="11pt">
							ABN:
							42253612817
						</fo:block>
					</fo:block-container>
					<fo:block-container height="1cm" width="6cm"
						top="0cm" left="16cm" position="absolute">
						<fo:block text-align="start" space-after.optimum="3pt">
							<fo:external-graphic src="./images/ka_sig.png" />
						</fo:block>
					</fo:block-container>
					<fo:block-container height="17cm" width="19cm"
						top="10.4cm" left="0cm" position="absolute">
						<fo:table border-collapse="separate" height="17cm"
							border-color="black" border-style="solid" border-width=".6mm"
							table-layout="fixed" width="100%">
							<fo:table-column column-width="5.5cm" />
							<fo:table-column column-width="5.5cm" />
							<fo:table-column column-width="7.5cm" />
							<fo:table-body font-family="sans-serif"
								font-weight="normal" font-size="15pt">
								<fo:table-row line-height="20pt">
									<fo:table-cell padding="1mm">
										<fo:block text-align="end"></fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="start">Balance Due</fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="center">$<xsl:value-of select="root/balanceDue" /></fo:block>
									</fo:table-cell>

								</fo:table-row>

							</fo:table-body>
						</fo:table>
					</fo:block-container>
					<fo:block-container height="17cm" width="19cm"
						top="13cm" left="0cm" position="absolute">
						<fo:table border-collapse="separate" height="17cm"
							table-layout="fixed" width="100%">
							<fo:table-column column-width="2.5cm" />
							<fo:table-column column-width="7.5cm" />
							<fo:table-column column-width="7.5cm" />
							<fo:table-body font-family="sans-serif"
								font-weight="normal" font-size="15pt">
								<fo:table-row line-height="20pt">
									<fo:table-cell padding="1mm">
										<fo:block text-align="end"></fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="start">Child’s Name / Number</fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="right">
											<xsl:value-of select="root/childName" />
											/
											<xsl:value-of select="root/childNumber" />
										</fo:block>
									</fo:table-cell>

								</fo:table-row>
								<fo:table-row line-height="20pt">
									<fo:table-cell padding="1mm">
										<fo:block text-align="end"></fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="start">Educator’s Number</fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="right">Q0008</fo:block>
									</fo:table-cell>

								</fo:table-row>
								<fo:table-row line-height="20pt">
									<fo:table-cell padding="1mm">
										<fo:block text-align="end"></fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="start">Week Period</fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="right">
											<xsl:value-of select="root/weekStart" />
											-
											<xsl:value-of select="root/weekEnd" />
										</fo:block>
									</fo:table-cell>

								</fo:table-row>
								<fo:table-row line-height="20pt">
									<fo:table-cell padding="1mm">
										<fo:block text-align="end"></fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="start">Current Balance</fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="right">$<xsl:value-of select="root/currentBalance" /></fo:block>
									</fo:table-cell>

								</fo:table-row>
								<fo:table-row line-height="20pt">
									<fo:table-cell padding="1mm">
										<fo:block text-align="end"></fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="start">Amount Paid</fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="right">$<xsl:value-of select="root/accountPaid" /></fo:block>
									</fo:table-cell>

								</fo:table-row>
								<fo:table-row line-height="20pt">
									<fo:table-cell padding="1mm">
										<fo:block text-align="end"></fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="start">Balance Due</fo:block>
									</fo:table-cell>
									<fo:table-cell padding="1mm">
										<fo:block text-align="right">$<xsl:value-of select="root/balanceDue" /></fo:block>
									</fo:table-cell>

								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block-container>

					<fo:block-container height="2cm" width="13.31cm"
						top="26.5cm" left="0cm" padding="1mm" position="absolute">
						<fo:block text-align="start" line-height="15pt"
							font-family="sans-serif" font-size="12pt">Thank you for choosing us.
						</fo:block>
					</fo:block-container>
					<fo:block-container height="2cm" width="6cm"
						top="23cm" left="14cm" position="absolute">
						<fo:block text-align="start" space-after.optimum="3pt">
							<fo:external-graphic src="./images/sign.png" />
						</fo:block>
					</fo:block-container>
					<fo:block-container height="2cm" width="6.0cm"
						top="26.5cm" left="14cm" padding="1mm" position="absolute">
						<fo:block text-align="start" space-after.optimum="3pt"
							line-height="25pt" font-family="sans-serif" font-size="14pt">OH IN
							KWON(ANN)
						</fo:block>
					</fo:block-container>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>
